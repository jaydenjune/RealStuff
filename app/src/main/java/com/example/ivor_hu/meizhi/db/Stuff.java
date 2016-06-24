package com.example.ivor_hu.meizhi.db;

import com.example.ivor_hu.meizhi.utils.DateUtil;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Ivor on 2016/2/28.
 */
public class Stuff extends RealmObject {
    private static final String TAG = "Stuff";
    @PrimaryKey
    @SerializedName("_id")
    private String id;
    private String desc, url, who, type;
    private Date publishedAt, lastChanged;
    private boolean isLiked;

    public Stuff() {
    }

    public Stuff(String id, String type, String desc, String url, String who, Date publishedAt) {
        this.id = id;
        this.type = type;
        this.desc = desc;
        this.url = url;
        this.who = who;
        this.publishedAt = publishedAt;
        this.lastChanged = publishedAt;
        this.isLiked = false;

    }

    public static Stuff fromSearch(SearchBean bean) throws ParseException {
        return new Stuff(
                bean.getUrl(),
                bean.getType(),
                bean.getDesc(),
                bean.getUrl(),
                bean.getWho(),
                DateUtil.parse(bean.getPublishedAt())
        );
    }

    public static RealmResults<Stuff> all(Realm realm, String type) {
        return realm.where(Stuff.class)
                .equalTo("type", type)
                .findAllSorted("publishedAt", Sort.DESCENDING);
    }

    public static RealmResults<Stuff> collections(Realm realm) {
        return realm.where(Stuff.class)
                .equalTo("isLiked", true)
                .findAllSorted("lastChanged", Sort.DESCENDING);
    }

    public static Stuff checkSearch(Realm realm, String url) {
        return realm.where(Stuff.class)
                .equalTo("id", url)
                .findFirst();
    }

    public static void clearAll(Realm realm) {
        final RealmResults<Stuff> allStuff = realm.where(Stuff.class)
                .findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                allStuff.deleteAllFromRealm();
            }
        });
    }

    public static void clearType(Realm realm, final String type) {
        final RealmResults<Stuff> types = realm.where(Stuff.class)
                .equalTo("type", type)
                .findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                types.deleteAllFromRealm();
            }
        });
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Date getLastChanged() {
        return lastChanged;
    }

    public void setLastChanged(Date lastChanged) {
        this.lastChanged = lastChanged;
    }

}
