package com.guavabot.marshpermissions.domain;

import android.Manifest;

import java.util.HashSet;
import java.util.Set;

public class DangerousPermissions {

    private static final Set<String> DANGEROUS_PERMISSIONS = new HashSet<>();
    private static final Set<String> CALENDAR = new HashSet<>();
    private static final Set<String> CAMERA = new HashSet<>();
    private static final Set<String> CONTACTS = new HashSet<>();
    private static final Set<String> LOCATION = new HashSet<>();
    private static final Set<String> MICROPHONE = new HashSet<>();
    private static final Set<String> PHONE = new HashSet<>();
    private static final Set<String> SENSORS = new HashSet<>();
    private static final Set<String> SMS = new HashSet<>();
    private static final Set<String> STORAGE = new HashSet<>();

    static {
        DANGEROUS_PERMISSIONS.add(Manifest.permission.GET_ACCOUNTS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_CONTACTS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.WRITE_CONTACTS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_CALENDAR);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.WRITE_CALENDAR);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.SEND_SMS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.RECEIVE_SMS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_SMS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.RECEIVE_WAP_PUSH);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.RECEIVE_MMS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.ACCESS_FINE_LOCATION);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_PHONE_STATE);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.CALL_PHONE);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.READ_CALL_LOG);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.WRITE_CALL_LOG);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.ADD_VOICEMAIL);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.USE_SIP);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.RECORD_AUDIO);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.CAMERA);
        DANGEROUS_PERMISSIONS.add(Manifest.permission.BODY_SENSORS);

        CONTACTS.add(Manifest.permission.GET_ACCOUNTS);
        CONTACTS.add(Manifest.permission.READ_CONTACTS);
        CONTACTS.add(Manifest.permission.WRITE_CONTACTS);
        CALENDAR.add(Manifest.permission.READ_CALENDAR);
        CALENDAR.add(Manifest.permission.WRITE_CALENDAR);
        SMS.add(Manifest.permission.SEND_SMS);
        SMS.add(Manifest.permission.RECEIVE_SMS);
        SMS.add(Manifest.permission.READ_SMS);
        SMS.add(Manifest.permission.RECEIVE_WAP_PUSH);
        SMS.add(Manifest.permission.RECEIVE_MMS);
        STORAGE.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        STORAGE.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        LOCATION.add(Manifest.permission.ACCESS_FINE_LOCATION);
        LOCATION.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        PHONE.add(Manifest.permission.READ_PHONE_STATE);
        PHONE.add(Manifest.permission.CALL_PHONE);
        PHONE.add(Manifest.permission.READ_CALL_LOG);
        PHONE.add(Manifest.permission.WRITE_CALL_LOG);
        PHONE.add(Manifest.permission.ADD_VOICEMAIL);
        PHONE.add(Manifest.permission.USE_SIP);
        PHONE.add(Manifest.permission.PROCESS_OUTGOING_CALLS);
        MICROPHONE.add(Manifest.permission.RECORD_AUDIO);
        CAMERA.add(Manifest.permission.CAMERA);
        SENSORS.add(Manifest.permission.BODY_SENSORS);
    }

    public static boolean isDangerous(String permission) {
        return DANGEROUS_PERMISSIONS.contains(permission);
    }

    public static String getPermissionGroup(String permission) {
        if (CONTACTS.contains(permission)) {
            return Manifest.permission_group.CONTACTS;
        } else if (CALENDAR.contains(permission)) {
            return Manifest.permission_group.CALENDAR;
        } else if (SMS.contains(permission)) {
            return Manifest.permission_group.SMS;
        } else if (STORAGE.contains(permission)) {
            return Manifest.permission_group.STORAGE;
        } else if (LOCATION.contains(permission)) {
            return Manifest.permission_group.LOCATION;
        } else if (PHONE.contains(permission)) {
            return Manifest.permission_group.PHONE;
        } else if (MICROPHONE.contains(permission)) {
            return Manifest.permission_group.MICROPHONE;
        } else if (CAMERA.contains(permission)) {
            return Manifest.permission_group.CAMERA;
        } else if (SENSORS.contains(permission)) {
            return Manifest.permission_group.SENSORS;
        }
        return null;
    }
}
