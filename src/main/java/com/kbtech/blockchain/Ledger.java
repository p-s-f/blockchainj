package com.kbtech.blockchain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//public class Ledger {

//    private static List<String> ledger = new LinkedList<>();
////    private Ledger() { }
////
////    private static Ledger instance;
////    private static boolean activated = false;
////
////    private static void createInstance() {
////        instance = new Ledger();
////        activated = true;
////    }
////
////    public static Ledger getInstance() {
////        if (!activated) {
////            createInstance();
////        }
////        return instance;
////    }
////
//    public static void storeMessage(String message) throws Exception {
//        ledger.add(message);
//    }
//
//    public static List<String> getLedger() {
//        return ledger;
//    }
//
//
//    private static Ledger instance = null;
//
//    private Ledger() {}
//
//    public static Ledger getInstance() {
//        if (instance == null) {
//            synchronized(Ledger.class) {
//                if (instance == null) {
//                    instance = new Ledger();
//                }
//            }
//        }
//
//        return instance;
//    }

public class Ledger {

    private Ledger() { }
    private static List<String> ledger = new LinkedList<>();

    private static class LedgerHolder {
        static final Ledger INSTANCE = new Ledger();
    }

    public static Ledger getInstance() {
        return LedgerHolder.INSTANCE;
    }

    public static void storeMessage(String message) throws Exception {
        ledger.add(message);
    }

    public static List<String> getLedger() {
        return ledger;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int messageNumber = 0;
        Iterator iter = ledger.iterator();
        while (iter.hasNext()) {
            stringBuilder.append(String.format("[%s] - %s", messageNumber, iter.next()));
        }
        return stringBuilder.toString();
    }
}

//}
