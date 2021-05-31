package com.eshop.analytics.streamprocessing;

import com.eshop.analytics.model.ProductSellCount;
import com.eshop.analytics.model.TopFiveProducts;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.io.*;
import java.util.Map;

class TopFiveSerde implements Serde<TopFiveProducts> {

  @Override
  public Serializer<TopFiveProducts> serializer() {

    return new Serializer<>() {
      @Override
      public void configure(final Map<String, ?> map, final boolean b) {
      }

      @Override
      public byte[] serialize(final String s, final TopFiveProducts topFiveProducts) {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final DataOutputStream dataOutputStream = new DataOutputStream(out);
        try {
          for (ProductSellCount productSellCount : topFiveProducts) {
            dataOutputStream.writeLong(productSellCount.getProductId());
            dataOutputStream.writeLong(productSellCount.getSells());
          }
          dataOutputStream.flush();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        return out.toByteArray();
      }
    };
  }

  @Override
  public Deserializer<TopFiveProducts> deserializer() {
    return (s, bytes) -> {
      if (bytes == null || bytes.length == 0) {
        return null;
      }
      final TopFiveProducts result = new TopFiveProducts();

      final DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(bytes));

      try {
        while (dataInputStream.available() > 0) {
          result.add(new ProductSellCount(dataInputStream.readLong(),
              dataInputStream.readLong()));
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return result;
    };
  }
}
