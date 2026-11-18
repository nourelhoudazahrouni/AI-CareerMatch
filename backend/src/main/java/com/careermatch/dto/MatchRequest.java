
package com.careermatch.dto;

import java.util.List;
import com.careermatch.model.Internship;

public record MatchRequest(
    String skills,
    List<Internship> internships
) {}
