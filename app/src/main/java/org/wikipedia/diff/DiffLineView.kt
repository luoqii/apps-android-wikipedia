package org.wikipedia.diff

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import org.wikipedia.R
import org.wikipedia.databinding.ItemDiffLineBinding
import org.wikipedia.dataclient.restbase.DiffResponse
import org.wikipedia.util.ResourceUtil

class DiffLineView constructor(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
    private val binding = ItemDiffLineBinding.inflate(LayoutInflater.from(context), this)
    private lateinit var diffLine: ArticleEditDetailsFragment.DiffLine

    init {
        layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        binding.collapseExpandButton.setOnClickListener {
            setExpanded(!diffLine.expanded)
        }
    }

    fun setItem(item: ArticleEditDetailsFragment.DiffLine) {
        diffLine = item
        binding.diffLineNumText.text = "Line " + diffLine.diff.lineNumber
        binding.diffText.text = diffLine.parsedText

        if (diffLine.diff.type == DiffResponse.DIFF_TYPE_LINE_WITH_SAME_CONTENT) {
            binding.diffLineNumText.setTextColor(ResourceUtil.getThemedColor(context, R.attr.material_theme_secondary_color))
            setExpanded(false)
        } else {
            binding.diffLineNumText.setTextColor(ResourceUtil.getThemedColor(context, R.attr.material_theme_primary_color))
            setExpanded(true)
        }
    }

    private fun setExpanded(expanded: Boolean) {
        diffLine.expanded = expanded
        if (expanded) {
            binding.diffText.isVisible = true
            binding.collapseExpandButton.setImageResource(R.drawable.ic_arrow_down_24)
        } else {
            binding.diffText.isVisible = false
            binding.collapseExpandButton.setImageResource(R.drawable.ic_chevron_forward_gray)
        }
    }
}
