package it.polimi.project14.common;

import java.time.LocalDateTime;
import java.util.Set;

public class SearchFilter implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private Set<String> capList;
    private String kind;
    private LocalDateTime expectedSince;
    private LocalDateTime expectedUntil;
    private boolean maxSeverity = false;

    public void setCapList(Set<String> capList) {
        this.capList = capList;
    }

    public Set<String> getCapList() {
        return this.capList;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return this.kind;
    }

    public void setExpectedSince(LocalDateTime expectedSince) {
        assert (this.expectedUntil == null || this.expectedUntil.isAfter(expectedSince));
        this.expectedSince = expectedSince;
    }

    public LocalDateTime getExpectedSince() {
        return this.expectedSince;
    }

    public LocalDateTime getExpectedUntil() {
        return expectedUntil;
    }

    public void setExpectedUntil(LocalDateTime expectedUntil) {
        assert (this.expectedSince == null || this.expectedSince.isBefore(expectedUntil));
        this.expectedUntil = expectedUntil;
    }

    public boolean isMaxSeverity() {
        return maxSeverity;
    }

    public void setMaxSeverity(boolean maxSeverity) {
        this.maxSeverity = maxSeverity;
    }
}
