package com.derevets.artem.model;

import com.derevets.artem.model.enums.Role;
import com.derevets.artem.model.jsonView.UserView;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Artem Derevets
 */
@Entity
@Table(name = "user_accounts")
@Data
@JsonPropertyOrder({
        "id", "email", "firstName", "lastName", "password"
})
public class User extends BaseEntity<UUID> implements Serializable {

    @JsonProperty("email")
    @Column(name = "email", length = 100, nullable = false, unique = true)
    @JsonView(UserView.ShortView.class)
    private String email;

    @JsonProperty("firstName")
    @Column(name = "first_name", length = 100, nullable = false)
    @JsonView(UserView.FullView.class)
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name", length = 100, nullable = false)
    @JsonView(UserView.FullView.class)
    private String lastName;

    @JsonProperty("password")
    @Column(name = "password", length = 255)
    @JsonView(UserView.FullView.class)
    private String password;

    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 20, nullable = false)
    private Role role;

    @Column(name = "is_account_non_locked", nullable = false)
    private Boolean isAccountNonLocked;

    @Column(name = "is_account_non_expired", nullable = false)
    private Boolean isAccountNonExpired;

    @Column(name = "is_credentials_non_expired", nullable = false)
    private Boolean isCredentialsNonLocked;


    @Column(name = "is_enabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "verification_code", unique = true)
    private Long verificationCode;

    public User() {
    }


    public static Builder getBuilder() {
        return new Builder();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", super.getId())
                .append("creationTime", this.getCreationTime())
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("modificationTime", this.getModificationTime())
                .append("version", this.getVersion())
                .append("role" + this.getRole())
                .toString();
    }

    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
            user.role = Role.ROLE_USER;
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public Builder isCredentialsNonLocked(Boolean isCredentialsNonLocked) {
            user.isCredentialsNonLocked = isCredentialsNonLocked;
            return this;
        }

        public Builder isAccountNonLocked(Boolean isAccountNonLocked) {
            user.isAccountNonLocked = isAccountNonLocked;
            return this;
        }

        public Builder isAccountNonExpired(Boolean isAccountNonExpired) {
            user.isAccountNonExpired = isAccountNonExpired;
            return this;
        }

        public Builder isEnabled(Boolean isEnabled) {
            user.isEnabled = isEnabled;
            return this;
        }

        public User build() {
            return user;
        }
    }
}
