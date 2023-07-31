package ru.tinkoff.tictactoe.gamechecker;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WinCheckerResults {
    private Boolean isWin;
    private String newGameField;
}
