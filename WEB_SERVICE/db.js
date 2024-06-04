const { Sequelize, Model, DataTypes } = require("sequelize");

const sequelize = new Sequelize("proyek_mdp", "root", "", {
  host: "localhost",
  dialect: "mysql",
});

class User extends Model {}
User.init(
  {
    username: {
      type: DataTypes.STRING,
      primaryKey: true,
      autoIncrement: false,
    },
    email: {
      type: DataTypes.STRING,
    },
    password: {
      type: DataTypes.STRING,
    },
    gender: {
      type: DataTypes.STRING,
    },
    specialist: {
      type: DataTypes.STRING,
    },
    alamat: {
      type: DataTypes.STRING,
    },
  },
  { sequelize, underscored: true, timestamps: false }
);

module.exports = {
  sequelize,
  User,
};
