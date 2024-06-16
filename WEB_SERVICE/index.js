const express = require("express");
const { User } = require("./db");
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
app.get("/admin/getluser",async function(req,res){
  
})
const port = 3000;
app.listen(port, function () {
  console.log(`Listening on port ${port}...`);
});
