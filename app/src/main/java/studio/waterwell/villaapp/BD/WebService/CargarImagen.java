package studio.waterwell.villaapp.BD.WebService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import studio.waterwell.villaapp.Modelo.ILugar;
import studio.waterwell.villaapp.R;

/**
 * Created by AdeFr on 15/05/2017.
 */

public class CargarImagen extends AsyncTask<String, Void, Bitmap> {

    private ILugar lugar;

    public CargarImagen (ILugar lugar) {
        this.lugar = lugar;
    }

    @Override
    protected Bitmap doInBackground(String... string) {
        URL imageUrl = null;
        HttpURLConnection conn = null;
        Bitmap imagen = null;
        try {

            imageUrl = new URL("http://www.villaApp.esy.es/img/" + string[0]);
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();
            imagen = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();

        }

        return imagen;
    }

    @Override
    protected void onPostExecute(Bitmap imagen_procesar) {
        lugar.cargarImagenLugar(imagen_procesar);
    }

}
