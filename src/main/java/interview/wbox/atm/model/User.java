package interview.wbox.atm.model;

/**
 * Created by ioana on 11/29/2020.
 */
import interview.wbox.atm.dto.UserDto;

import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;

    @Entity
    @Table(name = "user", schema="public")
    public class User {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        @Column(name = "id")
        private long id;
        @Column(name = "first_name")
        private String firstName;
        @Column(name = "last_name")
        private String lastName;
        @Column(name = "username", unique = true)
        private String username;
        @Column(name = "password")
        private String password;
        @Column(name = "email", unique = true)
        private String email;

        @Column(name = "telephone", unique = true)
        private String telephone;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "user_role",
                joinColumns =  @JoinColumn(name ="user_id"),inverseJoinColumns= @JoinColumn(name="role_id"))
        private Set<Role> roles;

        public User(){}
        public User(String firstName, String lastName, String username, String password,
                    String email, String telephone, Set<Role> roles){
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.password = password;
            this.email = email;
            this.telephone = telephone;
            this.roles = roles;
        }

        public Set<Role> getRoles() {
            return roles;
        }

        public void setRoles(Set<Role> roles) {
            this.roles = roles;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public UserDto toUserDto(){
            UserDto userDto = new UserDto();
            userDto.setId(this.id);
            userDto.setEmail(this.email);
            userDto.setTelephone(this.telephone);
            userDto.setFirstName(this.firstName);
            userDto.setLastName(this.lastName);
            userDto.setUsername(this.username);
            userDto.setRole(this.roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList()));
            return userDto;
        }
}
