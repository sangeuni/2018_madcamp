package org.weibeld.example.tabs;

public class TagsImg {
    int img; // 사진 - res/drawable
    Integer[] tags = {};

    public TagsImg(int img, Integer[] tags) {
        this.img = img;
        this.tags = tags;
    }

    public int getImg() {
        return img;
    }

    public Integer[] getTags() {
        return tags;
    }
}
