
import express from "express";
import OpenAI from "openai";

const app = express();
app.use(express.json({ limit: "20kb" }));

const apiKey = process.env.OPENAI_API_KEY;
const model = process.env.OPENAI_MODEL || "gpt-4o-mini";
const client = apiKey ? new OpenAI({ apiKey }) : null;

app.get("/health", (_req, res) => {
  res.json({
    ok: true,
    service: "robot-ai-kecil",
    aiConfigured: Boolean(client)
  });
});

app.post("/chat", async (req, res) => {
  const message =
    typeof req.body?.message === "string"
      ? req.body.message.trim()
      : "";

  if (!message) {
    return res.status(400).json({
      error: "Pertanyaan tidak boleh kosong."
    });
  }

  if (message.length > 4000) {
    return res.status(413).json({
      error: "Pesan terlalu panjang."
    });
  }

  if (!client) {
    return res.status(503).json({
      error: "Server AI belum dikonfigurasi."
    });
  }

  try {
    const response = await client.responses.create({
      model,
      instructions:
        "Kamu adalah ROBOT AI KECIL, asisten belajar yang ramah. Gunakan bahasa Indonesia sederhana, runtut, dan mendidik. Bantu pengguna memahami materi dan latihan, bukan menyontek saat ujian.",
      input: message,
      max_output_tokens: 700
    });

    res.json({
      reply:
        response.output_text ||
        "Maaf, belum ada jawaban yang dapat ditampilkan."
    });
  } catch (error) {
    console.error("AI error:", error.message);

    res.status(502).json({
      error: "Layanan AI sedang bermasalah. Coba lagi nanti."
    });
  }
});

const port = Number(process.env.PORT || 3000);

app.listen(port, "0.0.0.0", () => {
  console.log("Robot AI backend berjalan di port " + port);
});
