package com.medMais.infra.security;

import java.util.List;

public record TokenDataJWT(String Token,
						   List<String> roles) {}

