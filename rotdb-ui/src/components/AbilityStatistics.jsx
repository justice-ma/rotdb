import "../style/abilityStatistics.css";
import {
  Gauge,
  ArrowDownToLine,
  ArrowUpToLine,
  Swords,
  Sparkles,
  BarChart3,
} from "lucide-react";

import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJs,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { useEffect, useMemo, useState } from "react";

ChartJs.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
);

function StatTitle({ icon: Icon, children }) {
  return (
    <div className="stat-title">
      <Icon className="stat-icon" />
      <span>{children}</span>
    </div>
  );
}

function getMetricValue(hit, metric) {
  if (metric === "min") return hit.hitMinNonCrit ?? 0;
  if (metric === "max") return hit.hitMaxCrit ?? 0;
  return hit.hitAvgDamage ?? 0;
}

function isProcHit(hitType) {
  return (
    hitType === "PERFECTEQUILIBRIUM" ||
    hitType === "SPLITSOUL" ||
    hitType === "INSTABILITY"
  );
}

function buildStackedGroups(hits, metric) {
  const groups = [];

  for (const hit of hits) {
    const value = getMetricValue(hit, metric);
    const type = hit.hitType ?? "BASE";

    if (!isProcHit(type)) {
      groups.push({
        label: `Hit ${groups.length + 1}`,
        base: value,
        bolg: 0,
        splitSoul: 0,
        instability: 0,
        sourceHits: [hit],
      });
      continue;
    }

    const currentGroup = groups[groups.length - 1];

    if (!currentGroup) {
      groups.push({
        label: `Hit ${groups.length + 1}`,
        base: 0,
        bolg: type === "PERFECTEQUILIBRIUM" ? value : 0,
        splitSoul: type === "SPLITSOUL" ? value : 0,
        instability: type === "INSTABILITY" ? value : 0,
        sourceHits: [hit],
      });
      continue;
    }

    if (type === "PERFECTEQUILIBRIUM") {
      currentGroup.bolg += value;
    } else if (type === "SPLITSOUL") {
      currentGroup.splitSoul += value;
    } else if (type === "INSTABILITY") {
      if (metric !== "min") {
        currentGroup.instability += value;
      }
    }

    currentGroup.sourceHits.push(hit);
  }

  return groups;
}

export default function AbilityStatistics({
  calculationResults,
  selectedAbility,
}) {
  const [isTouchLayout, setIsTouchLayout] = useState(window.innerWidth <= 1200);

  useEffect(() => {
    function handleResize() {
      setIsTouchLayout(window.innerWidth <= 1200);
    }

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, []);

  const hits = calculationResults?.hit ?? [];
  const [metric, setMetric] = useState("avg");

  const groupedHits = useMemo(
    () => buildStackedGroups(hits, metric),
    [hits, metric],
  );

  const labels = useMemo(
    () => groupedHits.map((group) => group.label),
    [groupedHits],
  );

  const barChartData = useMemo(() => {
    const allDatasets = [
      {
        label: "Base Hit",
        data: groupedHits.map((group) => group.base),
        borderWidth: 1,
        backgroundColor: "rgba(63, 185, 233, 0.30)",
        borderColor: "rgba(63, 185, 233, 0.90)",
        borderRadius: 4,
        maxBarThickness: 28,
        stack: "damage",
      },
      {
        label: "BOLG Proc",
        data: groupedHits.map((group) => group.bolg),
        borderWidth: 1,
        backgroundColor: "rgba(255, 196, 61, 0.35)",
        borderColor: "rgba(255, 196, 61, 0.95)",
        borderRadius: 4,
        maxBarThickness: 28,
        stack: "damage",
      },
      {
        label: "Split Soul",
        data: groupedHits.map((group) => group.splitSoul),
        borderWidth: 1,
        backgroundColor: "rgba(168, 85, 247, 0.35)",
        borderColor: "rgba(168, 85, 247, 0.95)",
        borderRadius: 4,
        maxBarThickness: 28,
        stack: "damage",
      },
      {
        label: "Instability",
        data: groupedHits.map((group) => group.instability),
        borderWidth: 1,
        backgroundColor: "rgba(144, 244, 114, 0.35)",
        borderColor: "rgba(144, 244, 114, 0.95)",
        borderRadius: 4,
        maxBarThickness: 28,
        stack: "damage",
      },
    ];

    const datasets = allDatasets.filter((dataset) =>
      dataset.data.some((value) => value > 0),
    );

    return {
      labels,
      datasets,
    };
  }, [labels, groupedHits]);

  const baseChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    interaction: {
      mode: "nearest",
      intersect: true,
    },
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        mode: "nearest",
        intersect: true,
        position: "nearest",
        yAlign: "center",
        xAlign: "center",
        callbacks: {
          title: (tooltipItems) => {
            const item = tooltipItems?.[0];
            if (!item) return "";
            return groupedHits[item.dataIndex]?.label ?? "";
          },
          label: (context) => {
            const label = context.dataset.label ?? "";
            const value = context.parsed.y ?? 0;
            return `${label}: ${value}`;
          },
        },
      },
    },
    scales: {
      x: {
        stacked: true,
        grid: { color: "hsl(0 0% 30%)" },
        ticks: { color: "hsl(0 0% 95%)" },
        border: { color: "hsl(0 0% 30%)" },
      },
      y: {
        stacked: true,
        grid: { color: "hsl(0 0% 30%)" },
        ticks: { color: "hsl(0 0% 95%)" },
        border: { color: "hsl(0 0% 30%)" },
        beginAtZero: true,
      },
    },
  };

  const inlineChartData = useMemo(
    () => ({
      ...barChartData,
      datasets: barChartData.datasets.map((ds) => ({
        ...ds,
        barThickness: 10,
        maxBarThickness: 10,
      })),
    }),
    [barChartData],
  );

  const inlineOptions = {
    ...baseChartOptions,

    interaction: {
      mode: undefined,
    },

    plugins: {
      ...baseChartOptions.plugins,
      tooltip: {
        enabled: false,
      },
    },
  };

  const overlayOptions = {
    ...baseChartOptions,
    plugins: {
      ...baseChartOptions.plugins,
      legend: {
        ...baseChartOptions.plugins.legend,
        labels: {
          ...baseChartOptions.plugins.legend.labels,
          boxWidth: 16,
          padding: 18,
          font: {
            size: 13,
          },
        },
      },
      tooltip: {
        ...baseChartOptions.plugins.tooltip,
        titleFont: {
          size: 14,
        },
        bodyFont: {
          size: 13,
        },
        padding: 12,
      },
    },
    scales: {
      x: {
        ...baseChartOptions.scales.x,
        ticks: {
          ...baseChartOptions.scales.x.ticks,
          font: {
            size: 12,
          },
        },
      },
      y: {
        ...baseChartOptions.scales.y,
        ticks: {
          ...baseChartOptions.scales.y.ticks,
          font: {
            size: 12,
          },
        },
      },
    },
  };

  if (!selectedAbility) {
    return (
      <div className="detailed-ability-container">
        <h2 className="ability-header">Select an ability</h2>
        <p className="subtext">Pick an ability to see detailed stats.</p>
      </div>
    );
  }

  return (
    <div className="detailed-ability-container">
      <div>
        <h2 className="ability-header">{selectedAbility.name}</h2>
      </div>

      <div className="damage-card average-card">
        <div className="damage-head">
          <StatTitle icon={Gauge}>Overall Average Damage</StatTitle>
        </div>
        <p className="damage-result">{calculationResults.totalAvgDamage}</p>
        <p className="subtext">Expected damage per ability</p>
      </div>

      <div className="stats-grid">
        <div className="damage-card mini">
          <div className="damage-head">
            <StatTitle icon={ArrowDownToLine}>Average Min</StatTitle>
          </div>
          <p className="damage-result-small">
            {calculationResults.totalMinDamage}
          </p>
          <p className="subtext">Crit weighted</p>
        </div>

        <div className="damage-card mini">
          <div className="damage-head">
            <StatTitle icon={ArrowUpToLine}>Average Max</StatTitle>
          </div>
          <p className="damage-result-small">
            {calculationResults.totalMaxDamage}
          </p>
          <p className="subtext">Crit weighted</p>
        </div>
      </div>

      <div className="damage-card">
        <div className="damage-head">
          <StatTitle icon={Swords}>Non-Critical Strike Damage</StatTitle>
        </div>
        <div className="crit-damages">
          <div>
            <p className="subtext">Minimum</p>
            <p className="damage-result-small">
              {calculationResults.totalMinNonCrit}
            </p>
          </div>
          <div>
            <p className="subtext">Maximum</p>
            <p className="damage-result-small">
              {calculationResults.totalMaxNonCrit}
            </p>
          </div>
        </div>
      </div>

      <div className="damage-card crit-card">
        <div className="damage-head">
          <StatTitle icon={Sparkles}>Critical Strike Damage</StatTitle>
        </div>
        <div className="crit-damages">
          <div>
            <p className="subtext">Minimum</p>
            <p className="damage-result-small">
              {calculationResults.totalMinCrit}
            </p>
          </div>
          <div>
            <p className="subtext">Maximum</p>
            <p className="damage-result-small">
              {calculationResults.totalMaxCrit}
            </p>
          </div>
        </div>
      </div>

      <div className="damage-card chart-card">
        <div className="chart-header">
          <div className="damage-head" style={{ margin: 0 }}>
            <StatTitle icon={BarChart3}>Per Hit breakdown</StatTitle>
          </div>

          <select
            className="metric-select"
            value={metric}
            onChange={(e) => setMetric(e.target.value)}
          >
            <option value="min">Min</option>
            <option value="avg">Avg</option>
            <option value="max">Max</option>
          </select>
        </div>

        <div className="chart-inline">
          <Bar
            options={isTouchLayout ? overlayOptions : inlineOptions}
            data={isTouchLayout ? barChartData : inlineChartData}
          />
        </div>

        {!isTouchLayout ? (
          <div className="chart-overlay">
            <div className="chart-overlay-inner">
              <Bar options={overlayOptions} data={barChartData} />
            </div>
          </div>
        ) : null}
      </div>
    </div>
  );
}
