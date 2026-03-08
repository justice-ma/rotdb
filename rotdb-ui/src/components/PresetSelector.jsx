import { useState } from "react";
import "../style/presetSelector.css";

export default function PresetSelector({
  presets,
  selectedPresetId,
  setSelectedPresetId,
  onLoadPreset,
  onSavePreset,
  onDeletePreset,
}) {
  const [presetName, setPresetName] = useState("");

  function handleSaveClick() {
    const trimmed = presetName.trim();
    if (!trimmed) return;

    onSavePreset(trimmed);
    setPresetName("");
  }

  return (
    <div className="preset-selector">
      <div className="preset-row">
        <select
          className="preset-select"
          value={selectedPresetId}
          onChange={(e) => setSelectedPresetId(e.target.value)}
        >
          <option value="">Select preset...</option>
          {presets.map((preset) => (
            <option key={preset.id} value={preset.id}>
              {preset.name}
            </option>
          ))}
        </select>

        <button
          type="button"
          className="preset-button"
          onClick={onLoadPreset}
          disabled={!selectedPresetId}
        >
          Load
        </button>

        <button
          type="button"
          className="preset-button danger"
          onClick={onDeletePreset}
          disabled={!selectedPresetId}
        >
          Delete
        </button>
      </div>

      <div className="preset-row">
        <input
          className="preset-input"
          type="text"
          value={presetName}
          onChange={(e) => setPresetName(e.target.value)}
          placeholder="Preset name..."
          maxLength={60}
        />

        <button
          type="button"
          className="preset-button primary"
          onClick={handleSaveClick}
          disabled={!presetName.trim()}
        >
          Save
        </button>
      </div>

      {!presets.length ? (
        <div className="preset-empty">No saved presets yet.</div>
      ) : null}
    </div>
  );
}
