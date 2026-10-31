package com.robotai.kecil;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    LinearLayout root;
    int navy = Color.rgb(12, 22, 43);
    int blue = Color.rgb(35, 130, 255);
    int white = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHome();
    }

    GradientDrawable shape(int color, int radius) {
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(color);
        bg.setCornerRadius(radius);
        return bg;
    }

    TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setPadding(8, 8, 8, 8);
        return t;
    }

    Button makeButton(String value) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextColor(white);
        b.setAllCaps(false);
        b.setTextSize(16);
        b.setBackground(shape(blue, 24));
        return b;
    }

    void setup(String title) {
        ScrollView scroll = new ScrollView(this);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(22, 25, 22, 25);
        root.setBackgroundColor(navy);

        scroll.addView(root);
        setContentView(scroll);

        TextView heading = text(title, 23, white);
        heading.setTypeface(null, Typeface.BOLD);
        root.addView(heading);
    }

    void addSpace(int height) {
        View v = new View(this);
        root.addView(v, new LinearLayout.LayoutParams(
            1, height
        ));
    }

    void addMenu(String title, String subtitle, Runnable action) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(16, 14, 16, 14);
        card.setBackground(shape(Color.rgb(25, 42, 70), 22));

        TextView name = text(title, 18, white);
        name.setTypeface(null, Typeface.BOLD);

        TextView desc = text(subtitle, 13, Color.LTGRAY);

        card.addView(name);
        card.addView(desc);

        root.addView(card, new LinearLayout.LayoutParams(
            -1, -2
        ));

        card.setOnClickListener(v -> action.run());
        addSpace(12);
    }

    void showHome() {
        setup("ROBOT AI KECIL");

        TextView version = text("VERSION 2.0 • ASISTEN BELAJAR",
            12, Color.CYAN);
        root.addView(version);

        addSpace(15);

        TextView robot = text("🤖", 75, white);
        robot.setGravity(Gravity.CENTER);
        root.addView(robot);

        TextView welcome = text(
            "Halo! Aku Robot AI Kecil.\nSiap membantu kamu belajar?",
            19, white
        );
        welcome.setGravity(Gravity.CENTER);
        root.addView(welcome);

        addSpace(25);

        addMenu("💬 Chat AI",
            "Tanya materi dan pelajari berbagai topik.",
            () -> showChat());

        addMenu("📊 AUTO KKM",
            "Hitung nilai dan ketuntasan belajar.",
            () -> showKKM());

        addMenu("🧮 Kalkulator Nilai",
            "Hitung nilai berdasarkan hasil latihan.",
            () -> showCalculator());

        addMenu("📚 Latihan Soal",
            "Belajar melalui soal dan pembahasan.",
            () -> showPractice());

        addMenu("🕘 Riwayat Percakapan",
            "Lihat riwayat belajar kamu.",
            () -> showMessage("Riwayat",
                "Riwayat percakapan akan tersedia setelah penyimpanan dibuat."));

        addMenu("⚙️ Pengaturan",
            "Atur aplikasi ROBOT AI KECIL.",
            () -> showMessage("Pengaturan",
                "Pengaturan aplikasi akan kita lengkapi nanti."));
    }

    void backHome() {
        Button back = makeButton("← Kembali ke Beranda");
        root.addView(back);
        back.setOnClickListener(v -> showHome());
    }

    void showChat() {
        setup("💬 CHAT AI");

        root.addView(text(
            "Fitur AI sungguhan akan dihubungkan melalui backend pada tahap berikutnya.",
            16, white
        ));

        addSpace(15);

        EditText question = new EditText(this);
        question.setHint("Tulis pertanyaan belajar...");
        question.setTextColor(white);
        question.setHintTextColor(Color.LTGRAY);
        question.setBackground(shape(
            Color.rgb(25, 42, 70), 18
        ));
        root.addView(question);

        Button send = makeButton("Kirim Pertanyaan");
        root.addView(send);

        send.setOnClickListener(v -> {
            if (question.getText().toString().trim().isEmpty()) {
                Toast.makeText(this,
                    "Tulis pertanyaan terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                    "AI akan dihubungkan pada tahap berikutnya",
                    Toast.LENGTH_LONG).show();
            }
        });

        backHome();
    }

    void showKKM() {
        setup("📊 AUTO KKM");

        EditText score = new EditText(this);
        score.setHint("Masukkan nilai kamu");
        score.setInputType(2);
        score.setTextColor(white);
        root.addView(score);

        EditText kkm = new EditText(this);
        kkm.setHint("Masukkan nilai KKM");
        kkm.setInputType(2);
        kkm.setTextColor(white);
        root.addView(kkm);

        TextView result = text("", 17, Color.CYAN);
        root.addView(result);

        Button check = makeButton("Periksa Nilai");
        root.addView(check);

        check.setOnClickListener(v -> {
            try {
                int n = Integer.parseInt(
                    score.getText().toString()
                );
                int k = Integer.parseInt(
                    kkm.getText().toString()
                );

                if (n < 0 || n > 100 || k < 0 || k > 100) {
                    result.setText("Nilai harus antara 0–100.");
                } else if (n >= k) {
                    result.setText("TUNTAS! Kamu mencapai KKM.");
                } else {
                    result.setText(
                        "Belum tuntas. Kurang " + (k - n) + " poin."
                    );
                }
            } catch (Exception e) {
                result.setText("Masukkan nilai dengan benar.");
            }
        });

        backHome();
    }

    void showCalculator() {
        setup("🧮 KALKULATOR NILAI");

        EditText correct = new EditText(this);
        correct.setHint("Jumlah jawaban benar");
        correct.setInputType(2);
        correct.setTextColor(white);
        root.addView(correct);

        EditText total = new EditText(this);
        total.setHint("Jumlah seluruh soal");
        total.setInputType(2);
        total.setTextColor(white);
        root.addView(total);

        TextView result = text("", 18, Color.CYAN);
        root.addView(result);

        Button calculate = makeButton("Hitung Nilai");
        root.addView(calculate);

        calculate.setOnClickListener(v -> {
            try {
                double a = Double.parseDouble(
                    correct.getText().toString()
                );
                double b = Double.parseDouble(
                    total.getText().toString()
                );

                if (b <= 0 || a < 0 || a > b) {
                    result.setText("Periksa jumlah jawaban.");
                } else {
                    result.setText(
                        "Nilai kamu: " +
                        String.format("%.2f", a / b * 100)
                    );
                }
            } catch (Exception e) {
                result.setText("Masukkan angka dengan benar.");
            }
        });

        backHome();
    }

    void showPractice() {
        setup("📚 LATIHAN SOAL");

        root.addView(text(
            "Contoh latihan:\n\nBerapakah hasil 5 × 6?",
            19, white
        ));

        Button answer = makeButton("Lihat Pembahasan");
        root.addView(answer);

        answer.setOnClickListener(v ->
            showMessage("Pembahasan",
                "5 × 6 = 30. Hasil perkaliannya adalah 30.")
        );

        backHome();
    }

    void showMessage(String title, String message) {
        setup(title);
        root.addView(text(message, 16, white));
        backHome();
    }
          }

