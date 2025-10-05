package com.sp.ecommerce.shared.utils;

import com.github.luben.zstd.Zstd;

public class DocumentUtil {
    public static byte[] compress(byte[] data) {
        return Zstd.compress(data, 3); // level 3 = decent compression
    }

    public static byte[] decompress(byte[] compressedData, long originalSize) {
        if (originalSize <= 0) {
            throw new IllegalArgumentException("Original size must be positive");
        }
        return Zstd.decompress(compressedData, (int) originalSize);
    }
}
