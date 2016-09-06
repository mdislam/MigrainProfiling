package org.mis.daogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class Generator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1000, "org.mis.profiling.models");
        schema.setDefaultJavaPackageDao("org.mis.profiling.models.dao");

        addUser(schema);
        addMigrainEntry(schema);

        new DaoGenerator().generateAll(schema, "D:\\PROJECTS\\MigraineProfiling\\app\\src\\main\\java");
    }

    private static void addUser(Schema schema){
        Entity profile = schema.addEntity("User");
        profile.addIdProperty();
        profile.addStringProperty("name").unique();
        profile.addIntProperty("age");
        profile.addDoubleProperty("height");
        profile.addDoubleProperty("weight");
    }

    private static void addMigrainEntry(Schema schema){
        Entity migrainEntry = schema.addEntity("MigrainEntry");
        migrainEntry.addIdProperty().autoincrement();
        migrainEntry.addDateProperty("started").notNull();
        migrainEntry.addDateProperty("ended").notNull();
        migrainEntry.addIntProperty("level").notNull();
        migrainEntry.addStringProperty("timeofday");
        migrainEntry.addStringProperty("cause");
        migrainEntry.addStringProperty("remedy");
        migrainEntry.addStringProperty("medicine");
    }
}
