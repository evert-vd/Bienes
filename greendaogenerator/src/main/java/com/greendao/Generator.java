package com.greendao;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Property;
import org.greenrobot.greendao.generator.Schema;

public class Generator {
    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        /*version inicial del codigo
        Schema schema = new Schema(1, "com.apps.evertvd.inventariogreendao.database"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }

        */
        crearSchema();

    }

    private static void crearSchema(){
        int schemaVersion = 1;   // incrementar en cada nueva actualización del esquema.
        String dataPackage = "com.evertvd.bienes.modelo";   // ruta donde almacenar las clases-entidades.


        Schema schema = new Schema(schemaVersion, dataPackage);
        schema.setDefaultJavaPackageDao(dataPackage + ".dao");
        schema.enableKeepSectionsByDefault();   // con esto no sobreescribe código personal añadido en las clases de entidades.

        crearTablas(schema);

        try {
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    private static void addTables(final Schema schema) {
        addUserEntities(schema);
        // addPhonesEntities(schema);
    }

    // This is use to describe the colums of your table
    private static Entity addTables(final Schema schema) {
        Entity empresa = schema.addEntity("empresa");
        empresa.addIdProperty().primaryKey().autoincrement();
        empresa.addIntProperty("codempresa");
        empresa.addStringProperty("nombre");
        return empresa;
    }



    //    private static Entity addPhonesEntities(final Schema schema) {
    //        Entity phone = schema.addEntity("Phone");
    //        phone.addIdProperty().primaryKey().autoincrement();
    //        phone.addIntProperty("user_id").notNull();
    //        phone.addStringProperty("number");
    //        return phone;
    //    }

    */


    private static void crearTablas(Schema schema) {

        /* entidades */

        /* entidades */

        Entity empresa = schema.addEntity("Empresa");   // nombre de la tabla-entidad
        empresa.addIdProperty().primaryKey().autoincrement();   // columna id
        empresa.addStringProperty("empresa");

        Entity departamento = schema.addEntity("Departamento");
        departamento.addIdProperty().primaryKey().autoincrement();
        departamento.addStringProperty("departamento");
        Property empresaId = departamento.addLongProperty("empresa_id").index().getProperty();// clave foránea

        Entity sede = schema.addEntity("Sede");
        sede.addIdProperty().primaryKey().autoincrement();
        sede.addStringProperty("sede");
        Property departamentoId = sede.addLongProperty("departamento_id").index().getProperty();
        //Property departamentoEmpresaId = sede.addLongProperty("departamento_empresa_id").index().getProperty();


        Entity ubicacion = schema.addEntity("Ubicacion");
        ubicacion.addIdProperty().primaryKey().autoincrement();
        ubicacion.addStringProperty("ubicacion");
        Property sedeId = ubicacion.addLongProperty("sede_id").index().getProperty();

        Entity responsable=schema.addEntity("Responsable");
        responsable.addIdProperty().primaryKey().autoincrement();
        responsable.addStringProperty("responsable");


        Entity cuentacontable=schema.addEntity("CuentaContable");
        cuentacontable.addIdProperty().primaryKey().autoincrement();
        cuentacontable.addStringProperty("codigo");

        Entity centrocosto=schema.addEntity("CentroCosto");
        centrocosto.addIdProperty().primaryKey().autoincrement();
        centrocosto.addStringProperty("codigo");
        centrocosto.addStringProperty("centro");

        Entity catalogo=schema.addEntity("Catalogo");
        catalogo.addIdProperty().primaryKey().autoincrement();
        catalogo.addStringProperty("codigo");
        catalogo.addStringProperty("catalogo");
        Property empresaId2 = catalogo.addLongProperty("empresa_id2").index().getProperty();// clave foránea

        Entity activo = schema.addEntity("Activo");
        activo.addIdProperty().primaryKey().autoincrement();
        activo.addStringProperty("codigo").unique();
        activo.addStringProperty("activopadre");
        activo.addStringProperty("codigobarra");
        activo.addStringProperty("descripcion");
        activo.addStringProperty("placa");
        activo.addStringProperty("marca");
        activo.addStringProperty("modelo");
        activo.addStringProperty("serie");
        activo.addStringProperty("tipo");//af,bc,cv,rv
        activo.addStringProperty("expediente");
        activo.addStringProperty("ordencompra");
        activo.addStringProperty("factura");
        activo.addStringProperty("fechacompra");
        activo.addStringProperty("estado");

        Property ubicacionId = activo.addLongProperty("ubicacion_id").index().getProperty();   // clave foránea
        Property responsableId = activo.addLongProperty("responsable_id").index().getProperty();   // clave foránea
        Property cuentacontableId = activo.addLongProperty("cuentacontable_id").index().getProperty();   // clave foránea
        Property centrocostoId = activo.addLongProperty("centrocosto_id").index().getProperty();   // clave foránea
        Property catalogoId = activo.addLongProperty("catalogo_id").index().getProperty();   // clave foránea


        Entity historial = schema.addEntity("Historial");
        historial.addIdProperty().primaryKey().autoincrement();
        historial.addStringProperty("campo");
        historial.addStringProperty("fechamodificacion");
        Property activoId = historial.addLongProperty("activo_id").index().getProperty();  // clave foránea


 /*
        inventarioZona.addToOne(inventario, inventarioId);
        inventarioZona.addToOne(zona, zonaId);
        inventario.addToMany(inventarioZona, inventarioId);
        zona.addToMany(inventarioZona, zonaId);
       */

        departamento.addToOne(empresa, empresaId);
        empresa.addToMany(departamento, empresaId);

        sede.addToOne(departamento, departamentoId);
        //sede.addToOne(empresa,departamentoEmpresaId);
        departamento.addToMany(sede, departamentoId);


        ubicacion.addToOne(sede, sedeId);
        sede.addToMany(ubicacion, sedeId);


        activo.addToOne(ubicacion, ubicacionId);
        ubicacion.addToMany(activo, ubicacionId);

        activo.addToOne(responsable, responsableId);
        responsable.addToMany(activo, responsableId);

        activo.addToOne(cuentacontable, cuentacontableId);
        cuentacontable.addToMany(activo, cuentacontableId);

        activo.addToOne(centrocosto, centrocostoId);
        centrocosto.addToMany(activo, centrocostoId);

        catalogo.addToOne(empresa, empresaId2);
        empresa.addToMany(catalogo, empresaId2);

        activo.addToOne(catalogo, catalogoId);
        catalogo.addToMany(activo, catalogoId);


        historial.addToOne(activo, activoId);
        activo.addToMany(historial, activoId);


    }
}
