package com.healthcare.observer;

import com.healthcare.model.Prescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PatientNotificationObserver implements PrescriptionObserver {
    private static final Logger logger = LoggerFactory.getLogger(PatientNotificationObserver.class);

    @Override
    public void onPrescriptionGenerated(Prescription prescription) {
        logger.info("OBSERVER [Patient]: Notified Patient '{}' about new prescription #{}", 
            prescription.getPatient().getName(), prescription.getPrescriptionId());
    }
}
