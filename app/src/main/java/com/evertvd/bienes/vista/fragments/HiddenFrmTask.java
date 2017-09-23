package com.evertvd.bienes.vista.fragments;

import android.app.Fragment;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by evertvd on 22/09/2017.
 */

public class HiddenFrmTask extends Fragment {


    /*
    Interfaz para la comunicaciÃ³n con la actividad ABTest.
     */
    static interface TaskCallbacks {
        void onPreExecute();
        void onProgressUpdate(int progress);
        void onCancelled();
        void onPostExecute();
    }

    /*
    Instancia de la interfaz
     */
    private TaskCallbacks mCallbacks;

    /*
    Instancia de la tarea ProgressBarTask
     */
    ProgressBarTask progressBarTask;


    public HiddenFrmTask() {}

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // Obtener la instancia de ABTest
        //mCallbacks = (TaskCallbacks) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retener el fragmento creado
        setRetainInstance(true);

        /*
        Una vez creado el fragmento se inicia la tarea asincrona
         */
        progressBarTask = new ProgressBarTask();
        progressBarTask.execute();
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mCallbacks = null;
    }


    public class ProgressBarTask extends AsyncTask<Void, Integer, Long> {

        @Override
        protected void onPreExecute() {
            if (mCallbacks != null) {
                mCallbacks.onPreExecute();
            }
        }

        @Override
        protected void onCancelled() {
            if(mCallbacks != null)
                mCallbacks.onCancelled();
        }

        @Override
        protected Long doInBackground(Void... params) {
            long t0 = System.currentTimeMillis();


            int aux;
            int numbers[] = Login.numbers;

            for (int i = 0; i < numbers.length - 1; i++) {
                for (int j = 0; j < numbers.length -1; j++) {
                    if (numbers[j] > numbers[j+1])
                    {
                        aux          = numbers[j];
                        numbers[j]   = numbers[j+1];
                        numbers[j+1] = aux;
                    }
                }
                if(!isCancelled())
                    publishProgress((int)(((i+1)/(float)(numbers.length-1))*100));
                else break;

            }

            return t0;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            if (mCallbacks != null) {
                mCallbacks.onProgressUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(Long aLong) {
            if (mCallbacks != null) {
                mCallbacks.onPostExecute();
            }
        }


    }

}
