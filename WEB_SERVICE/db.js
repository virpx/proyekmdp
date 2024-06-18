// const { Sequelize, Model, DataTypes } = require("sequelize");

// const sequelize = new Sequelize("proyek_mdp", "root", "", {
//   host: "localhost",
//   dialect: "mysql",
// });

// class User extends Model {}
// User.init(
//   {
//     username: {
//       type: DataTypes.STRING,
//       allowNull: false,
//       primaryKey: true,
//     },
//     email: {
//       type: DataTypes.STRING,
//     },
//     fullname: {
//       type: DataTypes.STRING,
//     },
//     password: {
//       type: DataTypes.STRING,
//     },
//     gender: {
//       type: DataTypes.STRING,
//     },
//     specialist: {
//       type: DataTypes.STRING,
//     },

//   },
//   { sequelize, underscored: true, timestamps: false }
// );

// module.exports = {
//   sequelize,
//   User,
// };
const { Sequelize, DataTypes } = require("sequelize");
const sequelize = new Sequelize("proyek_mdp", "root", "", {
  host: "localhost",
  dialect: "mysql",
});

const DChat = sequelize.define(
  "DChat",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
    },
    id_hchat: {
      type: DataTypes.INTEGER,
    },
    pengirim: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    penerima: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    isi: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    attach_foodtrack: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
  },
  {
    tableName: "d_chat",
    timestamps: false,
    charset: "latin1",
  }
);

const FoodTrack = sequelize.define(
  "FoodTrack",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
    },
    username: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    nama: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    jumlah: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    calories: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    protein: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    sugar: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    carbs: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    fat: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    cholesterol: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    sodium: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    date_add: {
      type: DataTypes.STRING,
      allowNull: false,
      defaultValue: Sequelize.literal("CURRENT_TIMESTAMP"),
    },
  },
  {
    tableName: "food_track",
    timestamps: false,
    charset: "latin1",
  }
);

const HChat = sequelize.define(
  "HChat",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
    },
    user1: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    user2: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    selesai: {
      type: DataTypes.BOOLEAN,
      allowNull: false,
    },
    kesimpulan: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
  },
  {
    tableName: "h_chat",
    timestamps: false,
    charset: "latin1",
  }
);

const Resep = sequelize.define(
  "Resep",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
    },
    id_hchat: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    nama_obat: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    deskripsi_obat: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
  },
  {
    tableName: "resep",
    timestamps: false,
    charset: "latin1",
  }
);

const Review = sequelize.define(
  "Review",
  {
    id: {
      type: DataTypes.INTEGER,
      primaryKey: true,
      autoIncrement: true,
    },
    username_pengirim: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    username_target: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    isi: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    rating: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
  },
  {
    tableName: "review",
    timestamps: false,
    charset: "latin1",
  }
);

const User = sequelize.define(
  "User",
  {
    username: {
      type: DataTypes.STRING(255),
      primaryKey: true,
    },
    email: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    fullname: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    password: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    gender: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    specialist: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    sekolah: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    tahun_lulus: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    lama_praktik: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    foto_profile: {
      type: DataTypes.TEXT("long"),
      allowNull: false,
    },
    created_at: {
      type: DataTypes.DATE,
      allowNull: false,
    },
  },
  {
    tableName: "user",
    timestamps: false,
    charset: "latin1",
  }
);
const Dokterregis = sequelize.define(
  "Dokterregis",
  {
    username: {
      type: DataTypes.STRING(255),
      primaryKey: true,
    },
    email: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    fullname: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    password: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    gender: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    specialist: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    sekolah: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    tahun_lulus: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    lama_praktik: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    created_at: {
      type: DataTypes.DATE,
      allowNull: false,
    },
  },
  {
    tableName: "dokter_regis",
    timestamps: false,
    charset: "latin1",
  }
);
const Artikel = sequelize.define(
  "Artikel",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    judul: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    penulis: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    isi: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    image: {
      type: DataTypes.TEXT("long"),
      allowNull: false,
    },
    view: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    created_at: {
      type: DataTypes.DATE,
      allowNull: false,
      defaultValue: Sequelize.literal("CURRENT_TIMESTAMP"),
    },
  },
  {
    tableName: "artikel",
    timestamps: false,
    charset: "latin1",
  }
);
module.exports = {
  sequelize,
  DChat,
  FoodTrack,
  HChat,
  Resep,
  Review,
  User,
  Artikel,
  Dokterregis,
};
