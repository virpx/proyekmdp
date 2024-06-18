const express = require("express");
const bodyParser = require("body-parser");
const mysql = require("mysql");
const multer = require("multer");
const nodemailer = require("nodemailer");
const { translate } = require("@vitalets/google-translate-api");
const translatte = require("translatte");
const {
  DChat,
  FoodTrack,
  HChat,
  Resep,
  Review,
  User,
  Artikel,
  Dokterregis,
} = require("./db");
const { Sequelize, Op } = require("sequelize");
const bcrypt = require("bcrypt");
const axios = require("axios");
const app = express();
app.use(express.urlencoded({ extended: true, limit: "50mb" }));
app.use(express.json({ limit: "50mb" }));
const storage = multer.memoryStorage();
const upload = multer({ storage: storage });

const created_at = Date.now();

app.get("/users", async function (req, res) {
  const q = req.query.q || "";
  try {
    const users = await User.findAll({
      where: { username: { [Op.substring]: q } },
    });
    return res.status(200).send(users);
  } catch (error) {
    return res.status(500).send({ msg: "server error", error: error.message });
  }
});

app.get("/users/:username", async function (req, res) {
  const username = req.params.username;
  try {
    const user = await User.findByPk(username);
    if (!user) {
      return res.status(404).send({ msg: "not found" });
    } else {
      return res.status(200).send(user);
    }
  } catch (error) {
    return res.status(500).send({ msg: "server error", error: error.message });
  }
});

app.post("/users", async function (req, res) {
  const {
    username,
    email,
    fullname,
    password,
    gender,
    specialist,
    sekolah,
    tahun_lulus,
    lama_praktik,
    foto_profile,
  } = req.body;
  try {
    let user = await User.findByPk(username);
    if (user) {
      return res.status(400).send({ msg: "Duplicate username" });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    user = await User.create({
      username,
      email,
      fullname,
      password: hashedPassword,
      gender,
      specialist,
      sekolah,
      tahun_lulus,
      lama_praktik,
      created_at,
      foto_profile,
    });
    return res.status(201).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});

app.put("/users/:username", async function (req, res) {
  const {
    email,
    fullname,
    password,
    gender,
    specialist,
    sekolah,
    tahun_lulus,
    lama_praktik,
    foto_profile,
  } = req.body;
  const username = req.params.username;
  try {
    const user = await User.findByPk(username);
    if (!user) {
      return res.status(404).send({ msg: "Not found" });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    await user.update({
      email,
      fullname,
      password: hashedPassword,
      gender,
      specialist,
      sekolah,
      tahun_lulus,
      lama_praktik,
      created_at,
      foto_profile,
    });
    return res.status(200).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});

app.delete("/users/:username", async function (req, res) {
  const username = req.params.username;
  try {
    const user = await User.findByPk(username);
    if (!user) {
      return res.status(404).send({ msg: "not found" });
    } else {
      await user.destroy();
      return res.status(200).send(user);
    }
  } catch (error) {
    return res.status(500).send({ msg: "server error", error: error.message });
  }
});

app.post("/login", async function (req, res) {
  const { username, password } = req.body;
  try {
    const user = await User.findByPk(username);
    if (!user) {
      return res.status(404).send({ msg: "User not found" });
    }

    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) {
      return res.status(401).send({ msg: "Invalid credentials" });
    }

    if (user.specialist === "") {
      return res.status(200).send({ msg: "Login successful", role: "patient" });
    } else {
      return res.status(200).send({ msg: "Login successful", role: "doctor" });
    }
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});
app.get("/admin/getluser/", async function (req, res) {
  const user = await User.findAll();
  var datareturn = [];
  for (const iterator of user) {
    jenisakun = 1;
    if (iterator.specialist == "") {
      jenisakun = 0;
    }
    var databaru = {
      nama: iterator.fullname,
      jenis: jenisakun,
      username: iterator.username,
    };
    datareturn.push(databaru);
  }
  return res.status(200).json(datareturn);
});
app.get("/admin/lreview/:username", async function (req, res) {
  const username = req.params.username;
  var datareturn = [];
  const reviewe = await Review.findAll({
    where: {
      username_target: username,
    },
  });
  for (const iterator of reviewe) {
    const usere = await User.findOne({
      where: {
        username: iterator.username_pengirim,
      },
    });
    var databaru = {
      nama: usere.fullname,
      isi: iterator.isi,
      rating: iterator.rating,
    };
    datareturn.push(databaru);
  }
  return res.status(200).send(datareturn);
});
app.get("/admin/lartikel", async function (req, res) {
  const artikel = await Artikel.findAll();
  var datakembali = [];
  for (const iterator of artikel) {
    const usere = await User.findOne({
      where: {
        username: iterator.penulis,
      },
    });
    datakembali.push({
      id: iterator.id,
      judul: iterator.judul,
      author: usere.fullname,
      view: iterator.view,
    });
  }
  return res.status(200).json(datakembali);
});
app.get("/admin/ldokterregis", async function (req, res) {
  const artikel = await Dokterregis.findAll();
  var datakembali = [];
  for (const iterator of artikel) {
    if (iterator.specialist != "") {
      datakembali.push({
        username: iterator.username,
        nama: iterator.fullname,
        sekolahlulus: iterator.sekolah + " (" + iterator.tahun_lulus + ")",
        lama_praktik: iterator.lama_praktik,
        specialist: iterator.specialist,
      });
    }
  }
  return res.status(200).json(datakembali);
});
app.delete("/admin/hapusdokterregis/:username", async function (req, res) {
  const username = req.params.username;
  await Dokterregis.destroy({
    where: {
      username: username,
    },
  });
  return res.status(200).send("sukses");
});
app.get("/admin/accdokterregis/:username", async function (req, res) {
  const username = req.params.username;
  const datae = await Dokterregis.findOne({
    where: {
      username: username,
    },
  });
  await User.create({
    username: datae.username,
    email: datae.email,
    fullname: datae.fullname,
    password: datae.password,
    gender: datae.gender,
    specialist: datae.specialist,
    sekolah: datae.sekolah,
    tahun_lulus: datae.tahun_lulus,
    lama_praktik: datae.lama_praktik,
    created_at: datae.created_at,
  });
  await Dokterregis.destroy({
    where: {
      username: username,
    },
  });
  return res.status(200).send("sukses");
});
app.delete("/admin/hapusartikel/:id", async function (req, res) {
  const id = req.params.id;
  await Artikel.destroy({
    where: {
      id: id,
    },
  });
  return res.status(200).send("sukses");
});
app.delete("/admin/hapususer/:username", async function (req, res) {
  const username = req.params.username;
  await User.destroy({
    where: {
      username: username,
    },
  });
  return res.status(200).send("sukses");
});
app.get("/admin/homedashboard", async function (req, res) {
  const jumlahuser = await User.findAll();
  const jumlahartikel = await Artikel.findAll();
  const userperbulan = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
  for (const iterator of jumlahuser) {
    if (new Date(iterator.created_at).getFullYear == new Date().getFullYear) {
      userperbulan[new Date(iterator.created_at).getMonth()] += 1;
    }
  }
  return res.status(200).json({
    jumlahuser: jumlahuser.length,
    jumlahartikel: jumlahartikel.length,
    userperbulan: userperbulan,
  });
});

app.get("/getlistchat/:username", async function (req, res) {
  const username = req.params.username;
  const getchat = await HChat.findAll({
    where: {
      [Op.and]: [
        {
          [Op.or]: [{ user1: username }, { user2: username }],
        },
        {
          selesai: 0,
        },
      ],
    },
  });
  var keluaran = [];
  for (const iterator of getchat) {
    var username_lawan = iterator.user1;
    if (iterator.user1 == username) {
      username_lawan = iterator.user2;
    }
    const getnama = await User.findOne({
      where: {
        username: username_lawan,
      },
    });
    if (getnama.specialist != "") {
      namauser = "Dr " + getnama.fullname;
    } else {
      namauser = getnama.fullname;
    }
    keluaran.push({
      idhcat: iterator.id,
      gambar: "",
      username: username_lawan,
      nama: namauser,
    });
  }
  return res.status(200).send(keluaran);
});
function formatDate(isoString) {
  // Create a Date object from the ISO string
  const date = new Date(isoString);

  // Define month names
  const monthNames = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ];

  // Extract day, month, year, and time from the Date object
  const day = date.getUTCDate();
  const month = monthNames[date.getUTCMonth()];
  const year = date.getUTCFullYear();
  const hours = date.getUTCHours();
  const minutes = date.getUTCMinutes();

  // Format the time to ensure two digits for hours and minutes
  const formattedTime = `${hours.toString().padStart(2, "0")}:${minutes
    .toString()
    .padStart(2, "0")}`;

  // Construct the final formatted string
  return `${day} ${month} ${year} ${formattedTime}`;
}
function formatDate2(isoString) {
  // Create a Date object from the ISO string
  const date = new Date(isoString);

  // Define month names
  const monthNames = [
    "Jan",
    "Feb",
    "Mar",
    "Apr",
    "May",
    "Jun",
    "Jul",
    "Aug",
    "Sep",
    "Oct",
    "Nov",
    "Dec",
  ];

  // Extract day, month, year, and time from the Date object
  const day = date.getUTCDate();
  const month = monthNames[date.getUTCMonth()];
  const year = date.getUTCFullYear();
  const hours = date.getUTCHours();
  const minutes = date.getUTCMinutes();

  // Format the time to ensure two digits for hours and minutes
  const formattedTime = `${hours.toString().padStart(2, "0")}:${minutes
    .toString()
    .padStart(2, "0")}`;

  // Construct the final formatted string
  return `${day} ${month} ${year}`;
}
app.get("/getisichat/:idhchat", async function (req, res) {
  const hcatid = req.params.idhchat;
  const hasil = await DChat.findAll({
    where: {
      id_hchat: hcatid,
    },
  });
  var keluaran = [];
  for (const iterator of hasil) {
    attach_food = [];
    if (iterator.isi == "") {
      var datafoodtrack = iterator.attach_foodtrack.split(",");
      for (const iterator2 of datafoodtrack) {
        const fooddata = await FoodTrack.findOne({
          where: {
            id: iterator2,
          },
        });
        attach_food.push(fooddata);
        attach_food[attach_food.length - 1].date_add = formatDate(
          attach_food[attach_food.length - 1].date_add
        );
      }
    } else {
      attach_food.push({
        id: 0,
        username: "",
        nama: "",
        jumlah: 0,
        calories: 0,
        protein: 0,
        sugar: 0,
        carbs: 0,
        fat: 0,
        cholesterol: 0,
        sodium: 0,
        date_add: "",
      });
    }
    const data1 = await User.findOne({
      where: {
        username: iterator.pengirim,
      },
    });
    const data2 = await User.findOne({
      where: {
        username: iterator.penerima,
      },
    });
    keluaran.push({
      nama_pengirim: data1.fullname,
      nama_penerima: data2.fullname,
      pengirim: iterator.pengirim,
      penerima: iterator.penerima,
      isi: iterator.isi,
      attach_foodtrack: attach_food,
    });
  }
  return res.status(200).send(keluaran);
});

app.post("/dokter/uploadartikel", async (req, res) => {
  try {
    if (!req.body || !req.body) {
      return res.status(404).send({
        msg: "Invalid request. Make sure all required fields are provided, including the base64 encoded image.",
      });
    }

    const { judul, penulis, isi, view } = req.body;
    const image = req.body.image;

    const newArtikel = await Artikel.create({
      judul: judul,
      penulis: penulis,
      isi: isi,
      image: image,
      view: view,
      created_at: new Date(),
    });

    return res.status(200).send({
      msg: "Article uploaded successfully",
    });
  } catch (error) {
    console.error("Error uploading article:", error);
    return res.status(500).send({ msg: "Internal Server Error" });
  }
});

app.put("/dokter/:username", async function (req, res) {
  const { foto_profile, email, fullname, lama_praktik } = req.body;
  const username = req.params.username;
  try {
    const user = await User.findByPk(username);
    if (!user) {
      return res.status(404).send({ msg: "Not found" });
    }

    await user.update({
      foto_profile,
      email,
      fullname,
      lama_praktik,
    });
    return res.status(200).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});

app.post("/sendotp", async (req, res) => {
  let { email, otp } = req.body;
  const findUser = await User.findOne({ email: email });
  if (!findUser) {
    return res
      .status(400)
      .json({ message: "Account not found, you have to register first" });
  }
  const transporter = nodemailer.createTransport({
    host: "smtp.office365.com",
    port: 587,
    auth: {
      user: "learnfocusontarget@outlook.com",
      pass: "noreplylearnfocus222Q",
    },
    tls: {
      rejectUnauthorized: true,
    },
  });
  transporter.sendMail(
    {
      from: "learnfocusontarget@outlook.com", // verified sender email
      to: email, // recipient email
      subject: "Verification Code (OTP) to Reset Password", // Subject line
      text: "Verification Code (OTP) is " + otp, // plain text body
      // html: "<b>Hello world!</b>", // html body
    },
    function (error, info) {
      if (error) {
        console.log(error);
      } else {
        console.log("Email sent: " + info.response);
      }
    }
  );
  return res.status(200).json({ message: "Code sent to " + email });
});

app.post(
  "/user/addchatbiasa/:idhchat/:pengirim/:penerima",
  async function (req, res) {
    const { idhchat, pengirim, penerima } = req.params;
    const { isi } = req.body;
    await DChat.create({
      id_hchat: idhchat,
      pengirim: pengirim,
      penerima: penerima,
      isi: isi,
      attach_foodtrack: "",
    });
    return res.status(200).send("sukses");
  }
);
app.post(
  "/user/addchatbiasa/:idhchat/:pengirim/:penerima",
  async function (req, res) {
    const { idhchat, pengirim, penerima } = req.params;
    const { isi } = req.body;
    await DChat.create({
      id_hchat: idhchat,
      pengirim: pengirim,
      penerima: penerima,
      isi: isi,
      attach_foodtrack: "",
    });
    return res.status(200).send("sukses");
  }
);
app.get("/user/getfoodtrack/:username", async function (req, res) {
  const username = req.params.username;
  const datae = await FoodTrack.findAll({
    where: {
      username: username,
    },
    order: [["date_add", "DESC"]],
  });
  for (const iterator of datae) {
    iterator.date_add = formatDate2(iterator.date_add);
  }
  return res.status(200).send(datae);
});
app.get("/user/getfoodtrack/:username", async function (req, res) {
  const username = req.params.username;
  const datae = await FoodTrack.findAll({
    where: {
      username: username,
    },
    order: [["date_add", "DESC"]],
  });
  for (const iterator of datae) {
    iterator.date_add = formatDate2(iterator.date_add);
  }
  return res.status(200).send(datae);
});
app.post(
  "/user/addchatbiasa/:idhchat/:pengirim/:penerima",
  async function (req, res) {
    const { idhchat, pengirim, penerima } = req.params;
    const { isi } = req.body;
    await DChat.create({
      id_hchat: idhchat,
      pengirim: pengirim,
      penerima: penerima,
      isi: isi,
      attach_foodtrack: "",
    });
    return res.status(200).send("sukses");
  }
);
app.get("/user/getfoodtrack/:username", async function (req, res) {
  const username = req.params.username;
  const datae = await FoodTrack.findAll({
    where: {
      username: username,
    },
    order: [["date_add", "DESC"]],
  });
  for (const iterator of datae) {
    iterator.date_add = formatDate2(iterator.date_add);
  }
  return res.status(200).send(datae);
});

app.put("/changepassword/:email", async function (req, res) {
  const { password } = req.body;
  const email = req.params.email;
  try {
    const user = await User.findOne({
      where: { email: { [Op.eq]: email } },
    });
    if (!user) {
      return res.status(404).send({ msg: "Not found" });
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    await user.update({
      password: hashedPassword,
    });
    return res.status(200).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});

app.put("/user/:username", async function (req, res) {
  const { foto_profile, email, fullname } = req.body;
  const username = req.params.username;
  try {
    const user = await User.findByPk(username);
    if (!user) {
      return res.status(404).send({ msg: "Not found" });
    }

    await user.update({
      foto_profile,
      email,
      fullname,
    });
    return res.status(200).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});

app.get("/user/artikel", async function (req, res) {
  try {
    const artikel = await Artikel.findAll();
    if (!artikel) {
      return res.status(404).send({ msg: "not found" });
    } else {
      return res.status(200).send(artikel);
    }
  } catch (error) {
    return res.status(500).send({ msg: "server error", error: error.message });
  }
});

app.put("/updateViewArtikel", async function (req, res) {
  const { judul, penulis, isi } = req.body;
  try {
    const artikel = await Artikel.findOne({
      where: {
        judul: { [Op.eq]: judul },
        penulis: { [Op.eq]: penulis },
        isi: { [Op.eq]: isi },
      },
    });
    if (!artikel) {
      return res.status(404).send({ msg: "Not found" });
    }

    await artikel.update({
      view: artikel.view + 1,
    });
    return res.status(200).send(artikel);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});

app.get("/dokter/historyreview/:username_target", async function (req, res) {
  const username_target = req.params.username_target || "";
  try {
    const review = await Review.findAll({
      where: { username_target: { [Op.eq]: username_target } },
    });
    return res.status(200).send(review);
  } catch (error) {
    return res.status(500).send({ msg: "server error", error: error.message });
  }
});

app.get("/average-rating/:username_target", async (req, res) => {
  const username_target = req.params.username_target;

  try {
    const result = await Review.findAll({
      attributes: [
        [Sequelize.fn("AVG", Sequelize.col("rating")), "averageRating"],
      ],
      where: {
        username_target: username_target,
      },
    });

    const averageRating = result[0].dataValues.averageRating;
    return res.status(200).json({ username_target, averageRating });
  } catch (error) {
    console.log(error);
    return res.status(500).json({ error: "Internal Server Error" });
  }
});

app.post(
  "/user/addchatfoodtrack/:idhchat/:pengirim/:penerima",
  async function (req, res) {
    const { idhchat, pengirim, penerima } = req.params;
    const { isi } = req.body;
    await DChat.create({
      id_hchat: idhchat,
      pengirim: pengirim,
      penerima: penerima,
      isi: "",
      attach_foodtrack: isi,
    });
    return res.status(200).send("sukses");
  }
);
app.get("/user/searchfood", async function (req, res) {
  const cari = req.query.cari;
  const { text } = await translatte(cari, { to: "en" });
  console.log(text);
  const hasilnya = await axios.get(
    "https://api.calorieninjas.com/v1/nutrition?query=" + text,
    {
      headers: {
        "X-Api-Key": "cdh3uEi/kHaETOzigLob6w==IDo2pCTu0CyzeB4h",
      },
    }
  );
  const hasilgizi = hasilnya.data.items;
  var hasildata = {
    calories: 0,
    protein: 0,
    sugar: 0,
    carbs: 0,
    fat: 0,
    cholesterol: 0,
    sodium: 0,
  };
  for (const iterator of hasilgizi) {
    hasildata.calories += iterator.calories / 100;
    hasildata.protein += iterator.protein_g / 100;
    hasildata.sugar += iterator.sugar_g / 100;
    hasildata.carbs += iterator.carbohydrates_total_g / 100;
    hasildata.fat += iterator.fat_total_g / 100;
    hasildata.cholesterol += iterator.cholesterol_mg / 1000 / 100;
    hasildata.sodium += iterator.sodium_mg / 1000 / 100;
  }
  console.log(hasildata);
  return res.status(200).send(hasildata);
});
app.post("/user/addfoodtrack/:username", async function (req, res) {
  console.log(req.body);
  const {
    nama,
    jumlah,
    calories,
    protein,
    sugar,
    carbs,
    fat,
    cholesterol,
    sodium,
  } = req.body;
  const username = req.params.username;
  await FoodTrack.create({
    username: username,
    nama: nama,
    jumlah: jumlah,
    calories: calories,
    protein: protein,
    sugar: sugar,
    carbs: carbs,
    fat: fat,
    cholesterol: cholesterol,
    sodium: sodium,
  });
  return res.status(200).send("sukses");
});

app.post("/registerdokter", async function (req, res) {
  const {
    username,
    email,
    fullname,
    password,
    gender,
    specialist,
    sekolah,
    tahun_lulus,
    lama_praktik,
    foto_profile,
  } = req.body;
  try {
    let user = await User.findByPk(username);
    if (user) {
      return res.status(400).send({ msg: "Duplicate username" });
    }

    let dokter = await Dokterregis.findByPk(username);
    if (dokter) {
      return res.status(400).send({ msg: "Please Wait For Confirmation" });
    }

    const hashedPassword = await bcrypt.hash(password, 10);
    user = await Dokterregis.create({
      username,
      email,
      fullname,
      password: hashedPassword,
      gender,
      specialist,
      sekolah,
      tahun_lulus,
      lama_praktik,
      created_at,
      foto_profile,
    });
    return res.status(201).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});
app.get("/dokter/reviewuser/:idhcat/:usernamelawan/:username", async function (req, res) {
  const { idhcat, usernamelawan, username } = req.params
  const { isi,rating,kesimpulan } = req.query
  await Review.create({
    username_pengirim: username,
    username_target: usernamelawan,
    isi: isi,
    rating:rating,
  })
  await HChat.update(
    { selesai: 1,
    kesimpulan:kesimpulan },
    {
      where: {
        id:idhcat
      },
    },)
  return res.status(200).send("sukses")
})
const port = 3000;
app.listen(port, function () {
  console.log(`Listening on port ${port}...`);
});
