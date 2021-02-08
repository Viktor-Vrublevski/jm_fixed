package com.javamentor.qa.platform.dao.abstracts.dto;

import com.javamentor.qa.platform.models.dto.*;
import java.util.List;

public interface TagDtoDao {

    List<IgnoredTagDto> getIgnoredTagsByPrincipal(Long id);

    List<TrackedTagDto> getTrackedTagsByPrincipal(Long id);
}
