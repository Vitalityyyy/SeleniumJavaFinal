package org.example.listeners;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.model.TestResult;
import io.qameta.allure.model.TestResultContainer;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.example.managers.DriverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CucumberAllureListener implements ConcurrentEventListener {

    private String currentTestCaseId;
    private String currentContainerId;
    private final DriverManager driverManager = DriverManager.getDriverManager();
    private static final Logger logger = LoggerFactory.getLogger(CucumberAllureListener.class);

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::handleTestCaseStarted);
        publisher.registerHandlerFor(TestStepStarted.class, this::handleTestStepStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::handleTestStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::handleTestCaseFinished);
    }

    private void handleTestCaseStarted(TestCaseStarted event) {
        currentTestCaseId = UUID.randomUUID().toString();
        currentContainerId = UUID.randomUUID().toString();

        // Создаем контейнер для теста
        TestResultContainer container = new TestResultContainer()
                .setUuid(currentContainerId)
                .setName(event.getTestCase().getName());

        Allure.getLifecycle().startTestContainer(container);

        // Создаем тест-кейс
        TestResult testResult = new TestResult()
                .setUuid(currentTestCaseId)
                .setName(event.getTestCase().getName());

        Allure.getLifecycle().scheduleTestCase(testResult);
        Allure.getLifecycle().startTestCase(currentTestCaseId);
    }

    private void handleTestStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
            String stepName = step.getStep().getText();

            StepResult stepResult = new StepResult()
                    .setName(stepName)
                    .setStatus(Status.PASSED);

            Allure.getLifecycle().startStep(currentTestCaseId, UUID.randomUUID().toString(), stepResult);
        }
    }

    private void handleTestStepFinished(TestStepFinished event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            Status status = convertStatus(event.getResult().getStatus());

            Allure.getLifecycle().updateStep(step -> step.setStatus(status));

            if (status == Status.FAILED) {
                attachScreenshot();
            }

            Allure.getLifecycle().stopStep();
        }
    }

    private void handleTestCaseFinished(TestCaseFinished event) {
        Status status = convertStatus(event.getResult().getStatus());
        Allure.getLifecycle().updateTestCase(currentTestCaseId, result -> result.setStatus(status));
        Allure.getLifecycle().stopTestCase(currentTestCaseId);
        Allure.getLifecycle().writeTestCase(currentTestCaseId);
        Allure.getLifecycle().stopTestContainer(currentContainerId);
        Allure.getLifecycle().writeTestContainer(currentContainerId);
    }

    private Status convertStatus(io.cucumber.plugin.event.Status cucumberStatus) {
        switch (cucumberStatus) {
            case PASSED: return Status.PASSED;
            case FAILED: return Status.FAILED;
            case SKIPPED: return Status.SKIPPED;
            case PENDING: return Status.SKIPPED;
            case UNDEFINED: return Status.BROKEN;
            case AMBIGUOUS: return Status.BROKEN;
            default: return Status.PASSED;
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshot() {
        try {
            logger.info("Attempting to take screenshot...");
            WebDriver driver = driverManager.getDriver();

            if (driver == null) {
                logger.error("Driver is null!");
                return new byte[0];
            }

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            logger.info("Screenshot taken successfully, size: {} bytes", screenshot.length);
            return screenshot;
        } catch (Exception e) {
            logger.error("Failed to take screenshot", e);
            return new byte[0];
        }
    }
}