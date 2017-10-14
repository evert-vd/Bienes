package com.evertvd.bienes.tareas;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by evertvd on 14/10/2017.
 */

public class MainExecutor {


    public void ejecutar(){
        long init = System.currentTimeMillis();  // Instante inicial del procesamiento

    ExecutorService executor = Executors.newFixedThreadPool(2);

    Runnable tablas=new HiloExecutor();
    executor.execute(tablas);

    executor.shutdown();
        while (!executor.isTerminated()) {
            // Espero a que terminen de ejecutarse todos los procesos
            // para pasar a las siguientes instrucciones
        }

        long fin = System.currentTimeMillis();
        System.out.println("Tiempo total de procesamiento: "+(fin-init)/1000+" Segundos");
    }
}
