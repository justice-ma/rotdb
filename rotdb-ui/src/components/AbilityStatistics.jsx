import "../style/detailedAbility.css";
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
import { useMemo, useState } from "react";

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

export default function AbilityStatistics({
  calculationResults,
  selectedAbility,
}) {
  const hits = calculationResults?.hit ?? [];

  const [metric, setMetric] = useState("avg");

  const labels = useMemo(() => hits.map((_, i) => `Hit ${i + 1}`), [hits]);

  const dataValues = useMemo(() => {
    if (metric === "min") return hits.map((h) => h.hitMinNonCrit);
    if (metric === "max") return hits.map((h) => h.hitMaxCrit);
    return hits.map((h) => h.hitAvgDamage);
  }, [hits, metric]);

  const metricLabels = {
    min: "Minimum",
    avg: "Average",
    max: "Maximum",
  };

  const barChartData = useMemo(
    () => ({
      labels,
      datasets: [
        {
          label: metricLabels[metric],
          data: dataValues,
          borderWidth: 1,
          backgroundColor: "rgba(63, 185, 233, 0.3)",
          borderColor: "rgba(63, 185, 233, 0.9)",
          borderRadius: 4,
          maxBarThickness: 16,
          hoverBackgroundColor: "rgba(63, 185, 233, 0.6)",
          hoverBorderWidth: 3,
        },
      ],
    }),
    [labels, dataValues, metric],
  );

  const options = {
    plugins: {
      legend: {
        display: false,
      },
    },
    interaction: { mode: "index", intersect: false },
    onHover: (event, elements) => {
      const target =
        event?.native?.target || event?.chart?.canvas || event?.target;
      if (target)
        target.style.cursor = elements?.length ? "pointer" : "default";
    },
    scales: {
      x: {
        grid: { color: "hsl(0 0% 30%)" },
        ticks: { color: "hsl(0 0% 95%)" },
        border: { color: "hsl(0 0% 30%)" },
      },
      y: {
        grid: { color: "hsl(0 0% 30%)" },
        ticks: { color: "hsl(0 0% 95%)" },
        border: { color: "hsl(0 0% 30%)" },
        beginAtZero: true,
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
    <>
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

        <div className="bar-chart">
          <div className="chart-header">
            <div className="damage-head" style={{ margin: 0 }}>
              <StatTitle icon={BarChart3}>Hit breakdown</StatTitle>
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
          <Bar options={options} data={barChartData} />
        </div>
      </div>
    </>
  );
}
