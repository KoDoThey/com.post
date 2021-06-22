package com.vcc.internship.common.config;

/**
 * Biểu diễn cho một đối tượng là có thể cấu hình (config) được.
 * Một object có cài đặt interface này có thể được cấu hình từ
 * một tập các thuộc tính.
 *
 *
 */
public interface Configurable {

    /**
     * Cấu hình cho đối tượng từ tập các thuộc tính
     *
     * @param p tập các thuộc tính
     */
    void configure(Properties p);
}
