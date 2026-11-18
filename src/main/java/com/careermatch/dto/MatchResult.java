
package com.careermatch.dto;

public record MatchResult(
    Long id,
    String title,
    String company,
    String location,
    String requiredSkills,
    double score
) {}
