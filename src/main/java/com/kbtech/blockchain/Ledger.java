package com.kbtech.blockchain;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Ledger {

    private Ledger() { }
    private static final List<String> ledger = new LinkedList<>();

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
        for (String s : ledger) {
            stringBuilder.append(String.format("[%s] - %s", messageNumber, s));
        }
        return stringBuilder.toString();
    }
}
