package io.onicodes.issue_tracker.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;


enum IssueStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED
}


enum IssuePriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}


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
@Entity
public class Issue {

    @Id
    private @GeneratedValue Long id;
    @NotBlank
    private String title;

    public Issue() {}

    public Issue(String title) {
        this.title = title;
    }


    public Long getId() {
        return this.id;
    }

    
    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Issue{id=" + "title=" + this.title + "}";
    }
}
