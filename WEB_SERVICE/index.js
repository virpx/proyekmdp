const express = require("express");
const { DChat, FoodTrack, HChat, Resep, Review, User,Artikel,Dokterregis } = require("./db");
const { Op } = require("sequelize");
const bcrypt = require("bcrypt");
const app = express();
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

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
  const { username, email, fullname, password, gender, specialist } = req.body;
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
    });
    return res.status(201).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "Server error", error: error.message });
  }
});

app.put("/users/:username", async function (req, res) {
  const { email, fullname, password, gender, specialist } = req.body;
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
  const user = await User.findAll()
  var datareturn = []
  for (const iterator of user) {
    jenisakun = 1
    if (iterator.specialist == "") {
      jenisakun = 0
    }
    var databaru = {
      nama: iterator.fullname,
      jenis: jenisakun,
      username: iterator.username
    }
    datareturn.push(databaru)
  }
  return res.status(200).json(datareturn)
})
app.get("/admin/lreview/:username", async function (req, res) {
  const username = req.params.username;
  var datareturn = []
  const reviewe = await Review.findAll({
    where: {
      username_target: username
    }
  })
  for (const iterator of reviewe) {
    const usere = await User.findOne({
      where: {
        username: iterator.username_pengirim
      }
    })
    var databaru = {
      nama: usere.fullname,
      isi: iterator.isi
    }
    datareturn.push(databaru)
  }
  return res.status(200).send(datareturn)
})
app.get("/admin/lartikel", async function (req, res) {
  const artikel = await Artikel.findAll()
  var datakembali = []
  for (const iterator of artikel) {
    const usere = await User.findOne({
      where: {
        username: iterator.penulis
      }
    })
    datakembali.push({
      id:iterator.id,
      judul: iterator.judul,
      author: usere.fullname,
      view: iterator.view
    })
  }
  return res.status(200).json(datakembali)
})
app.get("/admin/ldokterregis", async function (req, res) {
  const artikel = await Dokterregis.findAll()
  var datakembali = []
  for (const iterator of artikel) {
    if (iterator.specialist != "") {
      datakembali.push({
        username:iterator.username,
        nama: iterator.fullname,
        sekolahlulus: iterator.sekolah+" ("+iterator.tahun_lulus+")",
        lama_praktik: iterator.lama_praktik,
        specialist: iterator.specialist,
      })
    }
  }
  return res.status(200).json(datakembali)
})
app.delete("/admin/hapusdokterregis/:username",async function(req,res){
  const username = req.params.username
  await Dokterregis.delete({
    where:{
      username:username
    }
  })
  return res.status(200).send("sukses")
})
app.get("/admin/accdokterregis/:username",async function(req,res){
  const username = req.params.username
  const datae = Dokterregis.findOne({
    where:{
      username:username
    }
  })
  await User.create(datae)
  return res.status(200).send("sukses")
})
app.delete("/admin/hapusartikel/:id",async function(req,res){
  const id = req.params.id
  await Dokterregis.delete({
    where:{
      id:id
    }
  })
  return res.status(200).send("sukses")
})
app.delete("/admin/hapususer/:username",async function(req,res){
  const username = req.params.username
  await User.delete({
    where:{
      username:username
    }
  })
  return res.status(200).send("sukses")
})
const port = 3000;
app.listen(port, function () {
  console.log(`Listening on port ${port}...`);
});
