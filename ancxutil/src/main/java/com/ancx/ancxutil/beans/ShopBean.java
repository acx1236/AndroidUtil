package com.ancx.ancxutil.beans;

import java.util.List;

/**
 * Created by ancx on 2016/11/4.
 */

public class ShopBean {

    private int errno;
    private String error;
    private ResultBean result;
    private String exec_time;
    private int server_time;
    private String server;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getExec_time() {
        return exec_time;
    }

    public void setExec_time(String exec_time) {
        this.exec_time = exec_time;
    }

    public int getServer_time() {
        return server_time;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public static class ResultBean {

        private List<RecBean> rec;

        private List<ComboBean> combo;

        private List<CuisineBean> cuisine;

        public List<RecBean> getRec() {
            return rec;
        }

        public void setRec(List<RecBean> rec) {
            this.rec = rec;
        }

        public List<ComboBean> getCombo() {
            return combo;
        }

        public void setCombo(List<ComboBean> combo) {
            this.combo = combo;
        }

        public List<CuisineBean> getCuisine() {
            return cuisine;
        }

        public void setCuisine(List<CuisineBean> cuisine) {
            this.cuisine = cuisine;
        }

        public static class RecBean extends GroupBean {

            private List<ListBean> list;

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private int id;
                private String title;
                private String mark;
                private String key_word;
                private String belong_title;
                private String type;
                private String price;
                private String image_url;
                private String content;
                private String unit;
                private int is_estimate_clear;
                private int is_free;
                private int is_weigh;
                private String weigh_nums;

                private List<AssortListBean> assort_list;

                private List<FlavorListBean> flavor_list;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMark() {
                    return mark;
                }

                public void setMark(String mark) {
                    this.mark = mark;
                }

                public String getKey_word() {
                    return key_word;
                }

                public void setKey_word(String key_word) {
                    this.key_word = key_word;
                }

                public String getBelong_title() {
                    return belong_title;
                }

                public void setBelong_title(String belong_title) {
                    this.belong_title = belong_title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getImage_url() {
                    return image_url;
                }

                public void setImage_url(String image_url) {
                    this.image_url = image_url;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public int getIs_estimate_clear() {
                    return is_estimate_clear;
                }

                public void setIs_estimate_clear(int is_estimate_clear) {
                    this.is_estimate_clear = is_estimate_clear;
                }

                public int getIs_free() {
                    return is_free;
                }

                public void setIs_free(int is_free) {
                    this.is_free = is_free;
                }

                public int getIs_weigh() {
                    return is_weigh;
                }

                public void setIs_weigh(int is_weigh) {
                    this.is_weigh = is_weigh;
                }

                public String getWeigh_nums() {
                    return weigh_nums;
                }

                public void setWeigh_nums(String weigh_nums) {
                    this.weigh_nums = weigh_nums;
                }

                public List<AssortListBean> getAssort_list() {
                    return assort_list;
                }

                public void setAssort_list(List<AssortListBean> assort_list) {
                    this.assort_list = assort_list;
                }

                public List<FlavorListBean> getFlavor_list() {
                    return flavor_list;
                }

                public void setFlavor_list(List<FlavorListBean> flavor_list) {
                    this.flavor_list = flavor_list;
                }

                public static class AssortListBean {
                    private int id;
                    private String title;
                    private String now_price;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getNow_price() {
                        return now_price;
                    }

                    public void setNow_price(String now_price) {
                        this.now_price = now_price;
                    }
                }

                public static class FlavorListBean {
                    private int id;
                    private String title;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }
            }
        }

        public static class ComboBean extends GroupBean {

            private List<ListBean> list;

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private int id;
                private String title;
                private String unit;
                private int is_estimate_clear;
                private String mark;
                private String key_word;
                private String belong_title;
                private String image_url;
                private String type;
                private String price;

                private List<DishListBean> dish_list;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public int getIs_estimate_clear() {
                    return is_estimate_clear;
                }

                public void setIs_estimate_clear(int is_estimate_clear) {
                    this.is_estimate_clear = is_estimate_clear;
                }

                public String getMark() {
                    return mark;
                }

                public void setMark(String mark) {
                    this.mark = mark;
                }

                public String getKey_word() {
                    return key_word;
                }

                public void setKey_word(String key_word) {
                    this.key_word = key_word;
                }

                public String getBelong_title() {
                    return belong_title;
                }

                public void setBelong_title(String belong_title) {
                    this.belong_title = belong_title;
                }

                public String getImage_url() {
                    return image_url;
                }

                public void setImage_url(String image_url) {
                    this.image_url = image_url;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public List<DishListBean> getDish_list() {
                    return dish_list;
                }

                public void setDish_list(List<DishListBean> dish_list) {
                    this.dish_list = dish_list;
                }

                public static class DishListBean {
                    private int id;
                    private String title;
                    private String image_url;
                    private String content;
                    private String unit;
                    private int is_free;
                    private int is_weigh;
                    private List<?> assort_list;
                    private List<?> flavor_list;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getImage_url() {
                        return image_url;
                    }

                    public void setImage_url(String image_url) {
                        this.image_url = image_url;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getUnit() {
                        return unit;
                    }

                    public void setUnit(String unit) {
                        this.unit = unit;
                    }

                    public int getIs_free() {
                        return is_free;
                    }

                    public void setIs_free(int is_free) {
                        this.is_free = is_free;
                    }

                    public int getIs_weigh() {
                        return is_weigh;
                    }

                    public void setIs_weigh(int is_weigh) {
                        this.is_weigh = is_weigh;
                    }

                    public List<?> getAssort_list() {
                        return assort_list;
                    }

                    public void setAssort_list(List<?> assort_list) {
                        this.assort_list = assort_list;
                    }

                    public List<?> getFlavor_list() {
                        return flavor_list;
                    }

                    public void setFlavor_list(List<?> flavor_list) {
                        this.flavor_list = flavor_list;
                    }
                }
            }
        }

        public static class CuisineBean extends GroupBean {

            private List<ListBean> list;

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                private int id;
                private String title;
                private String mark;
                private String key_word;
                private String belong_title;
                private String type;
                private String price;
                private String image_url;
                private String content;
                private String unit;
                private int is_estimate_clear;
                private int is_free;
                private int is_weigh;
                private String weigh_nums;

                private List<AssortListBean> assort_list;

                private List<FlavorListBean> flavor_list;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getMark() {
                    return mark;
                }

                public void setMark(String mark) {
                    this.mark = mark;
                }

                public String getKey_word() {
                    return key_word;
                }

                public void setKey_word(String key_word) {
                    this.key_word = key_word;
                }

                public String getBelong_title() {
                    return belong_title;
                }

                public void setBelong_title(String belong_title) {
                    this.belong_title = belong_title;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public String getImage_url() {
                    return image_url;
                }

                public void setImage_url(String image_url) {
                    this.image_url = image_url;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public int getIs_estimate_clear() {
                    return is_estimate_clear;
                }

                public void setIs_estimate_clear(int is_estimate_clear) {
                    this.is_estimate_clear = is_estimate_clear;
                }

                public int getIs_free() {
                    return is_free;
                }

                public void setIs_free(int is_free) {
                    this.is_free = is_free;
                }

                public int getIs_weigh() {
                    return is_weigh;
                }

                public void setIs_weigh(int is_weigh) {
                    this.is_weigh = is_weigh;
                }

                public String getWeigh_nums() {
                    return weigh_nums;
                }

                public void setWeigh_nums(String weigh_nums) {
                    this.weigh_nums = weigh_nums;
                }

                public List<AssortListBean> getAssort_list() {
                    return assort_list;
                }

                public void setAssort_list(List<AssortListBean> assort_list) {
                    this.assort_list = assort_list;
                }

                public List<FlavorListBean> getFlavor_list() {
                    return flavor_list;
                }

                public void setFlavor_list(List<FlavorListBean> flavor_list) {
                    this.flavor_list = flavor_list;
                }

                public static class AssortListBean {
                    private int id;
                    private String title;
                    private String now_price;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getNow_price() {
                        return now_price;
                    }

                    public void setNow_price(String now_price) {
                        this.now_price = now_price;
                    }
                }

                public static class FlavorListBean {
                    private int id;
                    private String title;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }
                }
            }
        }
    }
}
