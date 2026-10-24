#!/usr/bin/env python3
"""
AptechExam - Web Distribution & GitHub Pages Builder
Assembles the complete offline exam bank and interactive portal into a deployable static site.
"""

import os
import sys
import shutil
import argparse
import json
import re

# Ensure safe console output encoding on Windows terminals
if sys.stdout and hasattr(sys.stdout, "reconfigure"):
    try:
        sys.stdout.reconfigure(encoding="utf-8", errors="replace")
    except Exception:
        pass

def parse_args():
    parser = argparse.ArgumentParser(description="Assemble AptechExam static web portal for GitHub Pages")
    parser.add_argument("--assets", default="app/src/main/assets", help="Path to Android assets directory")
    parser.add_argument("--output", default="_site", help="Destination directory for static site (default: _site)")
    parser.add_argument("--clean", action="store_true", default=True, help="Clean output directory before build")
    return parser.parse_args()

def build_web(assets_dir, output_dir, clean=True):
    print("=" * 60)
    print("🚀 AptechExam Web Portal Builder")
    print(f"📁 Assets Source: {os.path.abspath(assets_dir)}")
    print(f"📦 Output Target: {os.path.abspath(output_dir)}")
    print("=" * 60)

    if not os.path.exists(assets_dir):
        print(f"❌ Error: Assets directory '{assets_dir}' not found!")
        sys.exit(1)

    portal_dir = os.path.join(assets_dir, "portal")
    if not os.path.exists(portal_dir):
        print(f"❌ Error: Portal directory '{portal_dir}' not found!")
        sys.exit(1)

    if clean and os.path.exists(output_dir):
        print(f"🧹 Cleaning previous build: {output_dir}")
        shutil.rmtree(output_dir)

    os.makedirs(output_dir, exist_ok=True)

    # 1. Copy all subject directories
    copied_subjects = 0
    copied_exams = 0
    total_files = 0

    for item in os.listdir(assets_dir):
        item_path = os.path.join(assets_dir, item)
        if os.path.isdir(item_path):
            dest_dir = os.path.join(output_dir, item)
            shutil.copytree(item_path, dest_dir, dirs_exist_ok=True)
            
            if item == "portal":
                continue
                
            copied_subjects += 1
            exam_files = [f for f in os.listdir(item_path) if f.endswith(".htm") or f.endswith(".html")]
            copied_exams += len(exam_files)

    # 2. Deploy Portal files to Root for instant GitHub Pages landing
    # This enables https://<username>.github.io/<repo>/ to serve the interactive catalog directly!
    portal_files = ["index.html", "portal.css", "portal.js"]
    for pf in portal_files:
        src = os.path.join(portal_dir, pf)
        dst = os.path.join(output_dir, pf)
        if os.path.exists(src):
            shutil.copy2(src, dst)
        else:
            print(f"⚠️ Warning: {pf} missing from portal directory!")

    # 3. Create .nojekyll to prevent GitHub Pages from ignoring files or using Jekyll
    nojekyll_path = os.path.join(output_dir, ".nojekyll")
    with open(nojekyll_path, "w", encoding="utf-8") as f:
        f.write("")

    # 4. Count total files and size
    total_bytes = 0
    for root, _, files in os.walk(output_dir):
        for f in files:
            total_files += 1
            total_bytes += os.path.getsize(os.path.join(root, f))

    # 5. Build Verification
    print("\n🔍 Verifying build integrity...")
    root_index = os.path.join(output_dir, "index.html")
    sub_index = os.path.join(output_dir, "portal", "index.html")
    root_css = os.path.join(output_dir, "portal.css")
    root_js = os.path.join(output_dir, "portal.js")

    assert os.path.exists(root_index), "Root index.html missing!"
    assert os.path.exists(sub_index), "Subfolder portal/index.html missing!"
    assert os.path.exists(root_css), "Root portal.css missing!"
    assert os.path.exists(root_js), "Root portal.js missing!"
    assert os.path.exists(nojekyll_path), ".nojekyll missing!"

    # Read manifest from portal.js to ensure all subjects exist
    with open(root_js, "r", encoding="utf-8") as f:
        js_content = f.read()

    manifest_match = re.search(r"const SUBJECTS_MANIFEST\s*=\s*(\[.*?\]);", js_content, re.DOTALL)
    if manifest_match:
        # Extract keys from manifest
        keys = re.findall(r'key:\s*"([^"]+)"', manifest_match.group(1))
        missing = []
        for k in keys:
            k_dir = os.path.join(output_dir, k)
            if not os.path.isdir(k_dir):
                missing.append(k)
        if missing:
            print(f"❌ Error: Missing subject directories: {missing}")
            sys.exit(1)
        print(f"✅ Verified all {len(keys)} manifest subjects exist in web distribution.")

    print("\n" + "=" * 60)
    print("🎉 Web Portal Build Complete!")
    print(f"📚 Total Subjects: {copied_subjects}")
    print(f"📝 Total Exam Papers: {copied_exams}")
    print(f"📄 Total Static Files: {total_files}")
    print(f"💾 Total Distribution Size: {total_bytes / (1024 * 1024):.2f} MB")
    print("=" * 60 + "\n")

if __name__ == "__main__":
    args = parse_args()
    build_web(args.assets, args.output, args.clean)
