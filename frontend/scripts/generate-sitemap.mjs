import { writeFile } from "node:fs/promises";
import { resolve } from "node:path";

const siteUrl = (
  process.env.SITE_URL ||
  process.env.VITE_SITE_URL ||
  "http://localhost:5173"
).replace(/\/$/, "");

const sitemap = `<?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
  <url>
    <loc>${siteUrl}/</loc>
    <changefreq>weekly</changefreq>
    <priority>1.0</priority>
  </url>
</urlset>
`;

await writeFile(resolve("public/sitemap.xml"), sitemap, "utf8");
