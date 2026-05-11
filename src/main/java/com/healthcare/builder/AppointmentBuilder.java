package com.healthcare.builder;

import com.healthcare.model.Appointment;
import com.healthcare.model.Doctor;
import com.healthcare.model.Patient;
import com.healthcare.enums.AppointmentStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentBuilder {
    private Appointment appointment;

    public AppointmentBuilder() {
        this.appointment = new Appointment();
        this.appointment.setStatus(AppointmentStatus.REQUESTED);
    }

    public AppointmentBuilder withPatient(Patient patient) {
        this.appointment.setPatient(patient);
        return this;
    }

    public AppointmentBuilder withDoctor(Doctor doctor) {
        this.appointment.setDoctor(doctor);
        return this;
    }

    public AppointmentBuilder onDate(String date) {
        this.appointment.setDate(LocalDate.parse(date));
        return this;
    }

    public AppointmentBuilder atTime(String time) {
        this.appointment.setTime(LocalTime.parse(time));
        return this;
    }

    public Appointment build() {
        return this.appointment;
    }
}
