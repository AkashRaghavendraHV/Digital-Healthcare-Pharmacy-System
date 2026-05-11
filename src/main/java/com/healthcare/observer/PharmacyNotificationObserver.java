package com.healthcare.observer;

import com.healthcare.model.Prescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PharmacyNotificationObserver implements PrescriptionObserver {
    private static final Logger logger = LoggerFactory.getLogger(PharmacyNotificationObserver.class);

    @Override
    public void onPrescriptionGenerated(Prescription prescription) {
        logger.info("OBSERVER [Pharmacy]: Prescription #{} is now available in the pharmacy queue for fulfillment.", 
            prescription.getPrescriptionId());
    }
}
