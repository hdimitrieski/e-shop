package com.eshop.gqlgateway.api.util;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.api.models.NodeId;

import java.util.Base64;
import java.util.UUID;

public final class IdUtils {

  public static String generateId(NodeType nodeType, UUID id) {
    final var input = nodeType.getName() + "_" + id.toString();
    return Base64.getEncoder().encodeToString(input.getBytes());
  }

  public static NodeId fromString(String encodedId) {
    final var decodedBytes = Base64.getDecoder().decode(encodedId);
    final var decodedString = new String(decodedBytes);
    final var parts = decodedString.split("_");

    if (parts.length != 2) throw new IllegalArgumentException("Invalid node id");

    return new NodeId(
      UUID.fromString(parts[1]),
      NodeType.of(parts[0]),
      encodedId
    );
  }

}
