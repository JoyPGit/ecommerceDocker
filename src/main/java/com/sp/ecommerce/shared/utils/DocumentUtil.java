package com.sp.ecommerce.shared.utils;

import com.github.luben.zstd.Zstd;

public class DocumentUtil {
    public static byte[] compress(byte[] data) {
        return Zstd.compress(data, 3); // level 3 = decent compression
    }

    public static byte[] decompress(byte[] compressed) {
        return Zstd.decompress(compressed, (int) Zstd.getFrameContentSize(compressed));
    }
}
