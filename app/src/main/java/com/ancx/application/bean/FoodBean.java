package com.ancx.application.bean;

import java.util.List;

/**
 * Created by ancx on 2016/11/10.
 */

public class FoodBean {

    private int group_count;

    private List<GroupBean> group;

    public int getGroup_count() {
        return group_count;
    }

    public void setGroup_count(int group_count) {
        this.group_count = group_count;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class GroupBean {
        private String kind;

        private List<CategoriesBean> categories;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public List<CategoriesBean> getCategories() {
            return categories;
        }

        public void setCategories(List<CategoriesBean> categories) {
            this.categories = categories;
        }

        public static class CategoriesBean {
            private int id;
            private String name;
            private String image_url;
            private int sub_category_count;
            private Object description;
            /**
             * id : 13
             * name : 包装谷薯
             * image_url : null
             */

            private List<SubCategoriesBean> sub_categories;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage_url() {
                return image_url;
            }

            public void setImage_url(String image_url) {
                this.image_url = image_url;
            }

            public int getSub_category_count() {
                return sub_category_count;
            }

            public void setSub_category_count(int sub_category_count) {
                this.sub_category_count = sub_category_count;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public List<SubCategoriesBean> getSub_categories() {
                return sub_categories;
            }

            public void setSub_categories(List<SubCategoriesBean> sub_categories) {
                this.sub_categories = sub_categories;
            }

            public static class SubCategoriesBean {
                private int id;
                private String name;
                private Object image_url;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Object getImage_url() {
                    return image_url;
                }

                public void setImage_url(Object image_url) {
                    this.image_url = image_url;
                }
            }
        }
    }
}
