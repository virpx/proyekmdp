const express = require("express");
const { User } = require("./db");
const { Op } = require("sequelize");
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
      return res.status(400).send({ msg: "duplicate username" });
    }
    user = await User.create({
      username,
      email,
      fullname,
      password,
      gender,
      specialist,
    });
    return res.status(201).send(user);
  } catch (error) {
    return res.status(500).send({ msg: "server error", error: error.message });
  }
});

app.put("/users/:username", async function (req, res) {
  const { email, fullname, password, gender, specialist } = req.body;
  const username = req.params.username;
  try {
    const user = await User.findByPk(username);
    if (!user) {
      return res.status(404).send({ msg: "not found" });
    } else {
      await user.update({ email, fullname, password, gender, specialist });
      return res.status(200).send(user);
    }
  } catch (error) {
    return res.status(500).send({ msg: "server error", error: error.message });
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

const port = 3000;
app.listen(port, function () {
  console.log(`Listening on port ${port}...`);
});
