package com.b2yblaster.groupcoza

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class ScriptHubActivity : AppCompatActivity() {

    private val scripts = listOf(
        "Infinite Jump" to "game:GetService('UserInputService').JumpRequest:Connect(function() game.Players.LocalPlayer.Character:FindFirstChildOfClass('Humanoid'):ChangeState(Enum.HumanoidStateType.Jumping) end)",
        "Speed Hack" to "game.Players.LocalPlayer.Character.Humanoid.WalkSpeed = 100",
        "Fly" to "local p = game.Players.LocalPlayer; local c = p.Character; local h = c.Humanoid; local t = c.HumanoidRootPart; local flying = true; local bg = Instance.new('BodyGyro', t); local bv = Instance.new('BodyVelocity', t); bg.MaxTorque = Vector3.new(9e9,9e9,9e9); bv.MaxForce = Vector3.new(9e9,9e9,9e9); bv.Velocity = Vector3.new(0,0,0); game:GetService('RunService').RenderStepped:Connect(function() if flying then bv.Velocity = workspace.CurrentCamera.CFrame.LookVector * 50 end end)",
        "NoClip" to "game:GetService('RunService').Stepped:Connect(function() game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame = game.Players.LocalPlayer.Character.HumanoidRootPart.CFrame end)",
        "ESP Players" to "for _,p in pairs(game.Players:GetPlayers()) do if p ~= game.Players.LocalPlayer then local bb = Instance.new('BillboardGui', p.Character.Head); bb.Size = UDim2.new(0,100,0,40); bb.StudsOffset = Vector3.new(0,2,0); bb.AlwaysOnTop = true; local tl = Instance.new('TextLabel', bb); tl.Size = UDim2.new(1,0,1,0); tl.Text = p.Name; tl.TextColor3 = Color3.new(1,0,0); tl.BackgroundTransparency = 1 end end"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }

        val scroll = ScrollView(this)
        scroll.addView(layout)
        setContentView(scroll)

        title = "GRoupCoza — Script Hub"

        scripts.forEach { (name, code) ->
            val btn = Button(this).apply {
                text = name
                setOnClickListener {
                    CoroutineScope(Dispatchers.IO).launch {
                        ExecutorService.execute(code)
                    }
                }
            }
            layout.addView(btn)
        }
    }
}
