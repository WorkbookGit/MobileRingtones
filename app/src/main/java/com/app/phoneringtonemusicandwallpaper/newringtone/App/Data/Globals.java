package com.app.phoneringtonemusicandwallpaper.newringtone.App.Data;

import android.media.MediaPlayer;

import com.app.phoneringtonemusicandwallpaper.newringtone.App.Data.Models.SoundModel;
import com.app.phoneringtonemusicandwallpaper.newringtone.R;

import java.util.ArrayList;


public class Globals {


    public static MediaPlayer mPlayerCategory;
    public static int[] ALARM_ICONS_BLACK = {R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn};
    public static String[] ALARM_NAMES = {"ALARM tone 1", "ALARM tone 2", "ALARM tone 3", "ALARM tone 4", "ALARM tone 5", "ALARM tone 6", "ALARM tone 7", "ALARM tone 8", "ALARM tone 9", "ALARM tone 10", "ALARM tone 11", "ALARM tone 12", "ALARM tone 13", "ALARM tone 14", "ALARM tone 15", "ALARM tone 16", "ALARM tone 17", "ALARM tone 18", "ALARM tone 19", "ALARM tone 20", "ALARM tone 21", "ALARM tone 22", "ALARM tone 23", "ALARM tone 24", "ALARM tone 25"};
    public static int[] ALRM_SOUNDS = {R.raw.alarm0};
    public static String[] ROMANTIC_NAMES = {"Ringtone 1", "Ringtone 2", "Ringtone 3", "Ringtone 4", "Ringtone 5", "Ringtone 6", "Ringtone 7", "Ringtone 8", "Ringtone 9", "Ringtone 10", "Ringtone 11", "Ringtone 12", "Ringtone 13", "Ringtone 14", "Ringtone 15", "Ringtone 16", "Ringtone 17", "Ringtone 18", "Ringtone 19", "Ringtone 20", "Ringtone 21", "Ringtone 22", "Ringtone 23", "Ringtone 24", "Ringtone 25", "Ringtone 26", "Ringtone 27", "Ringtone 28", "Ringtone 29", "Ringtone 30", "Ringtone 31", "Ringtone 32", "Ringtone 33", "Ringtone 34", "Ringtone 35", "Ringtone 36"};
    public static int[] RINGTONE_SOUNDS = {R.raw.r0, R.raw.r1, R.raw.r2, R.raw.r3, R.raw.r4, R.raw.r5, R.raw.r6, R.raw.r7, R.raw.r8, R.raw.r9, R.raw.r10, R.raw.r11, R.raw.r12, R.raw.r13, R.raw.r14, R.raw.r15, R.raw.r16, R.raw.r17, R.raw.r18, R.raw.r19, R.raw.r20, R.raw.r21, R.raw.r22, R.raw.r23, R.raw.r24, R.raw.r25, R.raw.r26, R.raw.r27, R.raw.r28, R.raw.r29, R.raw.r30, R.raw.r31, R.raw.r32, R.raw.r33, R.raw.r34, R.raw.r35};
    public static int[] SMS_ICONS_BLACK = {R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn, R.drawable.play_btn};
    public static String[] SMS_NAMES = {"SMStone 1", "SMStone 2", "SMStone 3", "SMStone 4", "SMStone 5", "SMStone 6", "SMStone 7", "SMStone 8", "SMStone 9", "SMStone 10", "SMStone 11", "SMStone 12", "SMStone 13", "SMStone 14", "SMStone 15", "SMStone 16", "SMStone 17", "SMStone 18", "SMStone 19", "SMStone 20", "SMStone 21", "SMStone 22", "SMStone 23"};
    public static int[] SMS_SOUNDS = {R.raw.sms0, R.raw.sms1, R.raw.sms2, R.raw.sms3, R.raw.sms4, R.raw.sms5, R.raw.sms6, R.raw.sms7, R.raw.sms8, R.raw.sms9, R.raw.sms10, R.raw.sms11, R.raw.sms12, R.raw.sms13, R.raw.sms14, R.raw.sms15, R.raw.sms16, R.raw.sms17, R.raw.sms18, R.raw.sms19, R.raw.sms20, R.raw.sms21, R.raw.sms22};


    public static ArrayList<SoundModel> localRingtoneList = new ArrayList();
    public static ArrayList<SoundModel> localSmsRingtoneList = new ArrayList();



    public static int[] Categories_image = {R.drawable.ic_latest_img, R.drawable.ic_popular_img, R.drawable.ic_hindi_img, R.drawable.ic_english_img, R.drawable.ic_load_img, R.drawable.ic_instrumental_img, R.drawable.ic_romantic_img};
    public static String[] Categories_name = {"Latest", "Popular", "Hindi", "English", "Lord", "Instrumental", "Romantic & Love"};

}
