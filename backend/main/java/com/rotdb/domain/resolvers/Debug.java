package com.rotdb.domain.resolvers;

import com.rotdb.domain.model.context.AbilityHitsContext;
import com.rotdb.domain.model.context.CalculationContext;

public final class Debug {
    private Debug() {}

    // ==== Column widths (tweak here, everything stays aligned) ====
    private static final int W_IDX   = 4;    // hit index / TOT label
    private static final int W_DOT   = 5;
    private static final int W_TIER  = 8;
    private static final int W_TIME  = 6;
    private static final int W_TYPE  = 22;   // fits PERFECTEQUILIBRIUM
    private static final int W_PAR   = 6;
    private static final int W_NUM   = 10;   // numeric columns

    // ==== Left-justified, 1 space padding rule ====
    // Exactly: "| <value padded right> " for each column.
    private static final String ROW_FMT =
            "| %-" + W_IDX  + "s " +
                    "| %-" + W_DOT  + "s " +
                    "| %-" + W_TIER + "s " +
                    "| %-" + W_TIME + "s " +
                    "| %-" + W_TYPE + "s " +
                    "| %-" + W_PAR  + "s " +
                    "| %-" + W_NUM  + "s " +   // curMin
                    "| %-" + W_NUM  + "s " +   // curMax
                    "| %-" + W_NUM  + "s " +   // curAvg
                    "| %-" + W_NUM  + "s " +   // ncMin
                    "| %-" + W_NUM  + "s " +   // ncMax
                    "| %-" + W_NUM  + "s " +   // ncAvg
                    "| %-" + W_NUM  + "s " +   // cMin
                    "| %-" + W_NUM  + "s " +   // cMax
                    "| %-" + W_NUM  + "s " +   // cAvg
                    "| %-" + W_NUM  + "s " +   // bolgMin
                    "| %-" + W_NUM  + "s " +   // bolgMax
                    "|%n";

    // Total printed width of a row (used for separators/banners)
    private static final int ROW_WIDTH =
            2 + W_IDX  + 2 +
                    2 + W_DOT  + 2 +
                    2 + W_TIER + 2 +
                    2 + W_TIME + 2 +
                    2 + W_TYPE + 2 +
                    2 + W_PAR  + 2 +
                    (2 + W_NUM + 2) * 11 + 1; // 11 numeric columns, plus final '|'

    private static String s(Object o) {
        return String.valueOf(o);
    }

    private static void line() {
        System.out.println("-".repeat(ROW_WIDTH));
    }

    private static void wideBanner(String title) {
        System.out.println("=".repeat(ROW_WIDTH));
        System.out.printf("%s%n", title);
        System.out.println("=".repeat(ROW_WIDTH));
    }

    // ====== Generic stage header/row/footer ======
    public static void stageHeader(CalculationContext context, String title) {
        if (!context.debug) return;

        wideBanner(title);
        System.out.printf("Ability: %s%n", context.getAbility().getName());
        line();
        System.out.printf(ROW_FMT,
                "hit", "dot", "tier", "time", "type", "par",
                "curMin", "curMax", "curAvg",
                "ncMin", "ncMax", "ncAvg",
                "cMin", "cMax", "cAvg",
                "bolgMin", "bolgMax"
        );
        line();
    }

    public static void stageRow(CalculationContext context, int i, AbilityHitsContext hit) {
        if (!context.debug) return;

        System.out.printf(ROW_FMT,
                s(i),
                s(hit.isDot()),
                s(hit.getTier()),
                s(hit.getHitTiming()),
                s(hit.getType()),
                s(hit.getParentIndex()),
                s(hit.getCurrentMin()),
                s(hit.getCurrentMax()),
                s(hit.getCurrentDamage()),
                s(hit.getNonCritMin()),
                s(hit.getNonCritMax()),
                s(hit.getNonCritDamage()),
                s(hit.getCritMin()),
                s(hit.getCritMax()),
                s(hit.getCritDamage()),
                s(hit.getBolgMin()),
                s(hit.getBolgMax())
        );
    }

    public static void stageFooter(CalculationContext context) {
        if (!context.debug) return;
        line();
    }

    public static void msg(CalculationContext context, String fmt, Object... args) {
        if (!context.debug) return;
        System.out.printf(fmt, args);
        System.out.println();
    }

    // ====== Final aggregation report ======
    public static void finalDamageReport(CalculationContext context) {
        if (!context.finalPrint) return;

        wideBanner("Final Damage (Aggregation)");
        System.out.printf("Ability: %s | Style: %s | BaseDamage: %d%n",
                context.getAbility().getName(),
                context.getEquipment().getCombatStyle(),
                context.getDamage().getBaseDamage()
        );

        line();

        // hit meta + 9 damage columns (cur/nc/crit)
        final int A = 4, T = 22, Ti = 6, P = 6, D = 5, N = 10;
        final String FMT =
                "| %-" + A + "s " +
                        "| %-" + T + "s " +
                        "| %-" + Ti + "s " +
                        "| %-" + P + "s " +
                        "| %-" + D + "s " +
                        "| %-" + N + "s " +  // curMin
                        "| %-" + N + "s " +  // curAvg
                        "| %-" + N + "s " +  // curMax
                        "| %-" + N + "s " +  // ncMin
                        "| %-" + N + "s " +  // ncAvg
                        "| %-" + N + "s " +  // ncMax
                        "| %-" + N + "s " +  // cMin
                        "| %-" + N + "s " +  // cAvg
                        "| %-" + N + "s " +  // cMax
                        "|%n";

        System.out.printf(FMT,
                "hit", "type", "time", "par", "dot",
                "curMin", "curAvg", "curMax",
                "ncMin", "ncAvg", "ncMax",
                "cMin", "cAvg", "cMax"
        );

        line();

        var hits = context.getAbility().getHits();
        for (int i = 0; i < hits.size(); i++) {
            AbilityHitsContext h = hits.get(i);

            System.out.printf(FMT,
                    i,
                    h.getType(),
                    h.getHitTiming(),
                    h.getParentIndex(),
                    h.isDot(),
                    h.getCurrentMin(), h.getCurrentDamage(), h.getCurrentMax(),
                    h.getNonCritMin(), h.getNonCritDamage(), h.getNonCritMax(),
                    h.getCritMin(), h.getCritDamage(), h.getCritMax()
            );
        }

        line();

        // TOTAL row (same columns)
        System.out.printf(FMT,
                "TOT", "TOTAL", "-", "-", "-",
                context.getDamage().getCurrentMin(), context.getDamage().getCurrentDamage(), context.getDamage().getCurrentMax(),
                context.getDamage().getNonCritMin(), context.getDamage().getNonCritDamage(), context.getDamage().getNonCritMax(),
                context.getDamage().getCritMin(), context.getDamage().getCritDamage(), context.getDamage().getCritMax()
        );

        line();
    }
}