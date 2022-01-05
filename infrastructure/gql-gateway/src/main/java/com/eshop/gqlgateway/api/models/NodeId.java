package com.eshop.gqlgateway.api.models;

import com.eshop.gqlgateway.api.NodeType;

import java.util.UUID;

public record NodeId(
  UUID id,
  NodeType type,
  String encodedId
) {
}
