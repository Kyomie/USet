package cn.ytang.james.uset.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import cn.ytang.james.uset.mvp.model.bean.SentenceImageText;
import cn.ytang.james.uset.mvp.model.bean.SentenceListDetail;

/**
 * 句子解析
 *
 * Created by James on 17/11/16.
 */

public class DocParseUtil {

    /**
     * 美图美句
     *
     * @param isFirst
     * @param result
     * @return
     */
    public static SentenceListDetail parseMeiju(boolean isFirst, String result) {

        Document doc = Jsoup.parse(result);

        SentenceListDetail sceneListDetail = new SentenceListDetail();

        // 仅第一次记录页数
        if (isFirst) {
            Elements pageLasts = doc.getElementsByClass("pager-last");
            if (pageLasts != null && pageLasts.size() > 0) {
                String page = pageLasts.first().text();
                sceneListDetail.page = page;
            }
        }

        List<SentenceImageText> sentenceImageTexts = new ArrayList<>();

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        Elements views_field_phpcodes = doc.getElementsByClass("views-field-phpcode");
        for (int i = 0; i < views_field_phpcodes.size(); i++) {
            Element views_field_phpcode = views_field_phpcodes.get(i);
            if (views_field_phpcode != null) {

                Element bdshare = views_field_phpcode.getElementById("bdshare");
                if (bdshare != null) {

                    String data = bdshare.attr("data");

                    try {
                        JSONObject jsonObject = new JSONObject(data);

                        SentenceImageText sentenceImageText = new SentenceImageText();
                        sentenceImageText.setText("" + jsonObject.get("text"));
                        sentenceImageText.setDesc("" + jsonObject.get("desc"));
                        sentenceImageText.setUrl("" + jsonObject.get("url"));
                        sentenceImageText.setPic("" + jsonObject.get("pic"));

                        sentenceImageTexts.add(sentenceImageText);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        sceneListDetail.mImageTexts = sentenceImageTexts;
        return sceneListDetail;
    }

}
