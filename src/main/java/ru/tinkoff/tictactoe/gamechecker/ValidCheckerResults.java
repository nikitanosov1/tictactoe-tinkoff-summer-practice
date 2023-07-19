package ru.tinkoff.tictactoe.gamechecker;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ValidCheckerResults {
    private Boolean isValid;
}
