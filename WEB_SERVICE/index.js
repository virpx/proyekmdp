const express = require("express");
const { User } = require("./db");
const { Op } = require("sequelize");
const app = express();
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

app.get("/Users", async function (req, res) {
  const q = req.query.q || "";
  const Users = await User.findAll({
    where: { username: { [Op.substring]: q } },
  });
  return res.status(200).send(Users);
});

// app.post("/Users", async function (req, res) {
//     const { nrp, name, ipk } = req.body;
//     let User = await User.findByPk(nrp);
//     if (User) {
//         return res.status(400).send({ msg: "duplicate nrp" });
//     }
//     User = await User.create({ nrp, name, ipk });
//     return res.status(201).send(User);
// });

// app.put("/Users/:nrp", async function (req, res) {
//     const { name, ipk } = req.body;
//     const nrp = req.params.nrp;
//     const User = await User.findByPk(nrp);
//     if (!User) {
//         return res.status(404).send({ msg: "not found" });
//     }
//     else {
//         await User.update({ name, ipk });
//         return res.status(200).send(User);
//     }
// });

// app.delete("/Users/:nrp", async function (req, res) {
//     const { name, ipk } = req.body;
//     const nrp = req.params.nrp;
//     const User = await User.findByPk(nrp);
//     if (!User) {
//         return res.status(404).send({ msg: "not found" });
//     }
//     else {
//         await User.destroy();
//         return res.status(200).send(User);
//     }
// });

const port = 3000;
app.listen(port, function () {
  console.log(`listening on port ${port}...`);
});
