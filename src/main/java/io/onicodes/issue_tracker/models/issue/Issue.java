package io.onicodes.issue_tracker.models.issue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import io.onicodes.issue_tracker.models.issueAssignee.IssueAssignee;


@Converter(autoApply = true)
class LowerCaseDBDataToUpperCaseAttributeConverter<T extends Enum<T>> implements AttributeConverter<T, String> {

    private Class<T> enumType;

    public LowerCaseDBDataToUpperCaseAttributeConverter(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return attribute != null ? attribute.name().toLowerCase() : null;
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return dbData != null ? Enum.valueOf(enumType, dbData.toUpperCase()) : null;
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                "Unknown value for enum " + enumType.getSimpleName() + ": " + dbData);
        }
    }

}


@Converter(autoApply = true)
class IssueStatusConverter extends LowerCaseDBDataToUpperCaseAttributeConverter<IssueStatus> {

    public IssueStatusConverter() {
        super(IssueStatus.class);
    }

}


@Converter(autoApply = true)
class IssuePriorityConverter extends LowerCaseDBDataToUpperCaseAttributeConverter<IssuePriority> {

    public IssuePriorityConverter() {
        super(IssuePriority.class);
    }

}


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Convert(converter = IssueStatusConverter.class)
    @Column(nullable = false)
    private IssueStatus status;

    @Convert(converter = IssuePriorityConverter.class)
    @Column(nullable = false)
    private IssuePriority priority;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<IssueAssignee> assignees = new HashSet<>();
}
