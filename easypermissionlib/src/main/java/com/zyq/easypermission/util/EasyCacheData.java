package com.zyq.easypermission.util;

import android.os.Parcelable;

import com.tencent.mmkv.MMKV;
import com.zyq.easypermission.EasyPermissionHelper;

import java.util.Collections;
import java.util.Set;


/**
 * 一些关键信息的存取（增加缓存）
 *
 * @author Zhang YanQiang
 * @date 2021/9/11　10:43.
 */
public class EasyCacheData {
    private static EasyCacheData instance;
    private static MMKV mv;

    private EasyCacheData() {
        if(EasyPermissionHelper.getInstance().alreadyInitialized){
            mv = MMKV.defaultMMKV();
        }
    }

    public static EasyCacheData getInstance() {
        if (instance == null) {
            synchronized (EasyCacheData.class) {
                if (instance == null) {
                    instance = new EasyCacheData();
                }
            }
        }
        return instance;
    }

    /**
     * 保存基本类型数据
     *
     * @param key
     * @param t
     */
    public void save(String key, Object t) {
        if(mv == null){
            return;
        }
        if (t instanceof String) {
            mv.encode(key, (String) t);
        } else if (t instanceof Integer) {
            mv.encode(key, (Integer) t);
        } else if (t instanceof Boolean) {
            mv.encode(key, (Boolean) t);
        } else if (t instanceof Float) {
            mv.encode(key, (Float) t);
        } else if (t instanceof Long) {
            mv.encode(key, (Long) t);
        } else if (t instanceof Double) {
            mv.encode(key, (Double) t);
        } else if (t instanceof byte[]) {
            mv.encode(key, (byte[]) t);
        } else {
            mv.encode(key, t.toString());
        }
    }

    /**
     * 保存
     *
     * @param key
     * @param sets
     */
    public void saveSet(String key, Set<String> sets) {
        if(mv == null){
            return;
        }
        mv.encode(key, sets);
    }

    /**
     * 保存Parcelable序列化的
     *
     * @param key
     * @param obj
     */
    public void saveParcelable(String key, Parcelable obj) {
        if(mv == null){
            return;
        }
        mv.encode(key, obj);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param key
     * @return
     */
    public Integer getInt(String key) {
        if(mv == null){
            return 0;
        }
        return mv.decodeInt(key, 0);
    }

    public Double getDouble(String key) {
        if(mv == null){
            return 0.00;
        }
        return mv.decodeDouble(key, 0.00);
    }

    public Long getLong(String key) {
        if(mv == null){
            return 0L;
        }
        return mv.decodeLong(key, 0L);
    }

    public Boolean getBoolean(String key) {
        if(mv == null){
            return false;
        }
        return getBoolean(key, false);
    }
    public Boolean getBoolean(String key, boolean defaultValue) {
        if(mv == null){
            return false;
        }
        return mv.decodeBool(key, defaultValue);
    }

    public Float getFloat(String key) {
        if(mv == null){
            return 0F;
        }
        return mv.decodeFloat(key, 0F);
    }

    public byte[] getBytes(String key) {
        if(mv == null){
            return new byte[0];
        }
        return mv.decodeBytes(key);
    }

    public String getString(String key) {
        if(mv == null){
            return "";
        }
        return mv.decodeString(key, "");
    }

    public Set<String> getStringSet(String key) {
        if(mv == null){
            return Collections.<String>emptySet();
        }
        return mv.decodeStringSet(key, Collections.<String>emptySet());
    }

    public Parcelable getParcelable(String key) {
        if(mv == null){
            return null;
        }
        return mv.decodeParcelable(key, null);
    }

    /**
     * 移除某个key对
     *
     * @param key
     */
    public void removeKey(String key) {
        if(mv == null){
            return;
        }
        mv.removeValueForKey(key);
    }

    /**
     * 清除所有key
     */
    public void clearAll() {
        if(mv == null){
            return;
        }
        mv.clearAll();
    }

}
