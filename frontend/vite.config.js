import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/abilities": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/buffs": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/prayers": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/perks": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/familiars": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/potions": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/damage": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
    },
  },
});
