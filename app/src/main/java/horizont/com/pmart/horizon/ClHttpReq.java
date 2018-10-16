package horizont.com.pmart.horizon;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ClHttpReq {
    private final static String URL_ENDPOINT = "http://hoz.horizont.co.th:88/";
    private final static String LOCAL_URL_ENDPOINT = "http://172.17.8.17:3000/";// 172.17.9.11 ,192.168.43.17

    private static String getUrlWithPart(String path) {
        Log.d("URL is: ", URL_ENDPOINT + path);
        return URL_ENDPOINT + path;
    }

    public static String getUrlWithPartLocal(String path) {
        Log.d("URL is: ", LOCAL_URL_ENDPOINT + path);
        return LOCAL_URL_ENDPOINT + path;
    }
    public static String getDataPosPdt(String path, String pPara) {
        String url = getUrlWithPart(path); //getUrlWithPath(path);   //"http://172.16.0.31:88/";//http://61.7.141.119:88/apimember?para=1;MjAxOC0wNi0yNyAxNzoyODowNC4zODg4OTk=
        URL object = null;
        HttpURLConnection con = null;
        StringBuilder st = new StringBuilder();
        try {
            // System.out.println("test");
            object = new URL(url);
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("mimeType", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
            con.connect();
            OutputStreamWriter input = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            input.write(pPara);
            input.flush();
            input.close();
            String output;
            BufferedReader br = null;
            System.out.println(con.getResponseCode());
            st.delete(0, st.length());
            if (con.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader((con.getInputStream()), "UTF-8"));
                while ((output = br.readLine()) != null) {
                    st.append(output);
                }
                System.out.println(con.getResponseCode() + "DATA : " + st.toString());
                con.disconnect();
                return st.toString();
            } else {
                Log.d("Log", "Failed to download file..");
                con.disconnect();
                return "error";
            }

        } catch (Exception e) {
            con.disconnect();
            Log.d(getDateAndTime() + "", String.valueOf(e));
            return "error";

        } finally {
            con.disconnect();
        }
    }

    public static String getDataPosPdtLocal(String path, String pPara) {
        String url = getUrlWithPartLocal(path); //getUrlWithPath(path);   //"http://172.16.0.31:88/";//http://61.7.141.119:88/apimember?para=1;MjAxOC0wNi0yNyAxNzoyODowNC4zODg4OTk=
        URL object = null;
        HttpURLConnection con = null;
        StringBuilder st = new StringBuilder();
        try {
            // System.out.println("test");
            object = new URL(url);
            con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setUseCaches(false);
            con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("mimeType", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; en-GB; rv:1.9.2.13) Gecko/20101203 Firefox/3.6.13 (.NET CLR 3.5.30729)");
            con.connect();
            OutputStreamWriter input = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            input.write(pPara);
            input.flush();
            input.close();
            String output;
            BufferedReader br = null;
            System.out.println(con.getResponseCode());
            st.delete(0, st.length());
            if (con.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader((con.getInputStream()), "UTF-8"));
                while ((output = br.readLine()) != null) {
                    st.append(output);
                }
                System.out.println(con.getResponseCode() + "DATA : " + st.toString());
                con.disconnect();
                return st.toString();
            } else {
                Log.d("HttpReq", "Failed to download file..");
                con.disconnect();
                return "error";
            }

        } catch (Exception e) {
            con.disconnect();
            Log.e(getDateAndTime() + "HttpReq", String.valueOf(e));
            return "error";

        } finally {
            con.disconnect();
        }
    }

    public static String getDateAndTime() {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH🇲🇲ss.S", Locale.ENGLISH);
            Date date = new Date();
            return dateFormat.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String fnPreparingSynDataPdt(String phone) {
        JsonObject jo = new JsonObject();
        JsonArray jaMember = new JsonArray();
        JsonArray jaCard = new JsonArray();
        JsonArray jaAddr = new JsonArray();
        StringBuilder sb = new StringBuilder();
        jo.addProperty("ERROR", "");
        jo.addProperty("STATUS", "");
        jo.addProperty("MODE", "VIEW");
        jo.addProperty("KEY", "MjAxOC0wNi0yNyAxNzoyODowNC4zODg4OTk=");
        jo.addProperty("SREC", "");
        jo.addProperty("EREC", "");
        jo.addProperty("TPAGE", "");
        jo.addProperty("RCOUNT", "");
        jo.addProperty("MKTC_KEY", "");
        jo.addProperty("MKTC_ID_CARD_NO", "");
        jo.addProperty("MCMA_CELL_PHONE1", phone);
        jo.addProperty("MCMC_NO", "");
        JsonObject joMember = new JsonObject();
        jaMember.add(joMember);
        JsonObject joCard = new JsonObject();
        jaCard.add(joCard);
        JsonObject joAddr = new JsonObject();
        jaAddr.add(joAddr);

        jo.add("member", jaMember);
        jo.add("card", jaCard);
        jo.add("address", jaAddr);
        return jo.toString();
    }

    public static String fnPreparingNotification() {
        JsonObject jo = new JsonObject();
        JsonArray jaNotifi = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jaNotifi.add(jsonObject);
        jo.add("notification", jaNotifi);
        return jo.toString();
    }
}
